package com.frankzhou.community.common.util.oss;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.frankzhou.community.common.base.ResultCodeConstant;
import com.frankzhou.community.common.exception.BusinessException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description minIO操作模板类
 * @date 2023-07-22
 */
@Slf4j
@AllArgsConstructor
public class MinioTemplate {

    private MinioClient minioClient;

    private OssProperties ossProperties;

    @PostConstruct
    public void init() {
        String defaultBucketName = ossProperties.defaultBucketName;
        if (bucketExist(defaultBucketName)) {
            log.info("默认存储桶已经存在");
        } else {
            makeBucket(defaultBucketName);
            log.info("默认存储桶已创建");
        }
    }

    /**
     * 列出所有的存储桶
     */
    @SneakyThrows
    public List<Bucket> listBucket() {
        return minioClient.listBuckets();
    }

    /**
     * 存储桶是否存在
     */
    @SneakyThrows
    public boolean bucketExist(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建一个桶
     */
    @SneakyThrows
    public void makeBucket(String bucketName) {
        if (!bucketExist(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 删除一个桶 如果桶中有对象，则会报错
     */
    @SneakyThrows
    public void deleteBucket(String bucketName) {
        if (bucketExist(bucketName)) {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 查询桶中的对象信息(是否递归查询)
     */
    @SneakyThrows
    public Iterable<Result<Item>> listBucketObjects(String bucketName,boolean recursive) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(recursive).build());
    }

    /**
     * 存入一个对象
     */
    @SneakyThrows
    public OssFileInfo putObjects(InputStream inputStream, String bucketName, String originalFilename) {
        String uuidFilename = generateFileName(originalFilename);
        if (StringUtils.isBlank(bucketName)) {
            bucketName = ossProperties.defaultBucketName;
        }

        if (!bucketExist(bucketName)) {
            throw new BusinessException(ResultCodeConstant.BUCKET_NOT_EXIST_ERROR);
        }

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(uuidFilename)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
            OssFileInfo ossFile = OssFileInfo.builder()
                    .ossFilePath(uuidFilename)
                    .originalFileName(originalFilename)
                    .build();
            return ossFile;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 获取一个文件 需要对Object有读权限
     */
    @SneakyThrows
    public InputStream getObjects(String bucketName, String ossFilePath) {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(ossFilePath).build());
    }

    /**
     * 返回一个带签名、过期时间的Get请求URL
     */
    @SneakyThrows
    public String getPreSignedObjectUrl(String bucketName, String ossFilePath) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(ossFilePath)
                        .expiry(60 * 60 * 24)
                        .build());
    }

    /**
     * 返回一个带签名的临时上传元数据对象，前端获取后可以直接访问minIO上传文件
     */
    @SneakyThrows
    public Map<String, String> getPreSignedPostFormData(String bucketName, String fileName) {
        // 为存储桶创建一个上传策略，过期时间为7天
        PostPolicy policy = new PostPolicy(bucketName, ZonedDateTime.now().plusDays(7));
        // 设置一个参数key，值为上传对象的名称
        policy.addEqualsCondition("key", fileName);
        // 添加Content-Type以"image/"开头，表示只能上传照片
        policy.addStartsWithCondition("Content-Type", "image/");
        // 设置上传文件的大小 64kiB to 10MiB.
        policy.addContentLengthRangeCondition(64 * 1024, 10 * 1024 * 1024);
        return minioClient.getPresignedPostFormData(policy);
    }

    private String getDownloadUrl(String bucket, String pathFile) {
        return ossProperties.getEndpoint() + StrUtil.SLASH + bucket + pathFile;
    }

    public String generateFileName(String originalFilename) {
        String prefix = "uid";
        String uuid = UUID.fastUUID().toString();
        String uuidFilename = prefix + StrUtil.SLASH + uuid + StrUtil.SLASH
                + DateUtil.format(new Date(),"yyyy-MM-dd") + StrUtil.SLASH + originalFilename;
        return uuidFilename;
    }

    public String getEndPoint() {
        return ossProperties.endpoint;
    }
}
