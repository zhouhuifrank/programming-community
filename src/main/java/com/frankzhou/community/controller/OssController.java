package com.frankzhou.community.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.common.constant.ErrorConstant;
import com.frankzhou.community.common.exception.BusinessException;
import com.frankzhou.community.common.util.oss.OssProperties;
import com.frankzhou.community.common.util.oss.OssType;
import com.frankzhou.community.common.util.oss.domain.OssFileVO;
import com.frankzhou.community.model.dto.oss.OssDeleteDTO;
import com.frankzhou.community.model.dto.oss.OssDownloadDTO;
import com.frankzhou.community.model.dto.oss.OssUploadDTO;
import com.frankzhou.community.model.vo.OssFileVO;
import com.frankzhou.community.service.OssService;
import io.minio.*;
import io.minio.http.Method;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储控制器
 * @date 2023-07-22
 */
@Slf4j
@RestController
@RequestMapping("/oss")
@Api(tags = {"对象存储"})
public class OssController {

    @Resource
    private OssService ossService;

    @Resource
    private MinioClient minioClient;

    @ApiOperation(value = "文件上传", notes = "后端服务器上传OSS")
    @PostMapping("/upload")
    public ResultDTO<OssFileVO> uploadFile(MultipartFile file) {
        String bucketName = "test";
        String originalFilename = file.getOriginalFilename();
        String prefix = "uid";
        String uuid = UUID.fastUUID().toString();
        String uuidFilename = prefix + StrUtil.SLASH + uuid + StrUtil.SLASH
                + DateUtil.format(new Date(),"yyyy-MM-dd") + StrUtil.SLASH + originalFilename;

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                throw new BusinessException("存储桶不存在");
            }

            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(uuidFilename)
                    .stream(inputStream,inputStream.available(),-1)
                    .build());
        } catch (Exception e) {
            log.error("minio occur error");
        }

        OssFileVO fileVO = OssFileVO.builder()
                .fileName(originalFilename)
                .filePath(uuidFilename)
                .build();

        return ResultDTO.getSuccessResult();
    }

    @ApiOperation(value = "获取文件上传URL", notes = "前端上传OSS")
    @PostMapping("/url")
    public ResultDTO<OssFileVO> getFileUploadUlr(@RequestBody OssUploadDTO uploadDTO) {
        if (ObjectUtil.isNotNull(uploadDTO)) {
            throw new BusinessException("文件上传请求参数错误");
        }

        String bucketName = uploadDTO.getBucketName();
        String ossFilePath = uploadDTO.getOssFilePath();
        String originalFilename = uploadDTO.getOriginalFilename();
        Boolean autoPath = uploadDTO.getAutoPath();
        if (StringUtils.isAnyBlank(bucketName,ossFilePath)) {
            throw new BusinessException("文件上传参数为空");
        }

        String prefix = "uid";
        String uuid = UUID.fastUUID().toString();
        String uuidFilename = prefix + StrUtil.SLASH + uuid + StrUtil.SLASH
                + DateUtil.format(new Date(),"yyyy-MM-dd") + StrUtil.SLASH + originalFilename;
        String uuidFilePath = autoPath.equals(Boolean.TRUE) ?  uuidFilename : ossFilePath;

        String preSignedUploadUrl = "";
        try {
            preSignedUploadUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(uuidFilePath)
                            .expiry(60 * 60 * 24)
                            .build());
        } catch (Exception e) {
            log.error("minio occur error");
        }

        String downloadUrl = "http://115.159.216.169:9000" + StrUtil.SLASH + bucketName + uuidFilePath;

        OssFileVO fileVO = OssFileVO.builder()
                .uploadUlr(preSignedUploadUrl)
                .downloadUrl(downloadUrl)
                .build();
        return ResultDTO.getSuccessResult(fileVO);
    }

    @ApiOperation(value = "获取文件", notes = "获取文件")
    @PostMapping("/download")
    public void downloadFile(@RequestBody OssDownloadDTO downloadDTO, HttpServletResponse response) {
        // TODO 待开发
    }


    @ApiOperation(value = "删除文件", notes = "删除OSS上的文件")
    @PostMapping("/delete")
    public ResultDTO<Boolean> deleteFile(@RequestBody OssDeleteDTO deleteDTO) {
        if (ObjectUtil.isNotNull(deleteDTO)) {
            throw new BusinessException("请求参数错误");
        }


        String bucketName = deleteDTO.getBucketName();
        String ossFilePath = deleteDTO.getOssFilePath();
        if (StringUtils.isAnyBlank(bucketName,ossFilePath)) {
            throw new BusinessException("删除文件参数不能为空");
        }

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(ossFilePath).build());
        } catch (Exception e) {
            log.error("minio occur error");
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

}
