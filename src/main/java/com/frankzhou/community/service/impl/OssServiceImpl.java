package com.frankzhou.community.service.impl;

import cn.hutool.core.util.StrUtil;
import com.frankzhou.community.common.base.ResultCodeConstant;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.common.enums.OssBucketEnum;
import com.frankzhou.community.common.exception.BusinessException;
import com.frankzhou.community.common.util.AssertUtil;
import com.frankzhou.community.common.util.oss.MinioTemplate;
import com.frankzhou.community.common.util.oss.OssFileInfo;
import com.frankzhou.community.manager.OssManager;
import com.frankzhou.community.model.dto.oss.OssDeleteDTO;
import com.frankzhou.community.model.dto.oss.OssDownloadDTO;
import com.frankzhou.community.model.dto.oss.OssUploadDTO;
import com.frankzhou.community.model.vo.OssFileVO;
import com.frankzhou.community.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description Oss对象存储服务实现类
 * @date 2023-07-22
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Resource
    private MinioTemplate minioTemplate;

    @Resource
    private OssManager ossManager;

    @Override
    public ResultDTO<OssFileVO> uploadFile(MultipartFile file) {
        AssertUtil.isNotNull(file, ResultCodeConstant.REQUEST_PARAM_ERROR);

        OssFileInfo ossFileInfo = null;
        try {
            String bucketName = OssBucketEnum.Test.getBucketName();
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            ossFileInfo = minioTemplate.putObjects(inputStream, bucketName, originalFilename);
        } catch (Exception e) {
            log.error("file upload error");
            throw new BusinessException("文件上传失败");
        }

        // 将文件信息存到本地oss_file表中

        OssFileVO fileVO = OssFileVO.builder()
                .fileName(ossFileInfo.getOriginalFileName())
                .filePath(ossFileInfo.getOssFilePath())
                .build();

        return ResultDTO.getSuccessResult(fileVO);
    }

    @Override
    public ResultDTO<OssFileVO> getUploadUrl(OssUploadDTO uploadDTO) {
        AssertUtil.isNotNull(uploadDTO,ResultCodeConstant.REQUEST_PARAM_ERROR);

        String bucketName = uploadDTO.getBucketName();
        String ossFilePath = uploadDTO.getOssFilePath();
        String originalFilename = uploadDTO.getOriginalFilename();
        Boolean autoPath = uploadDTO.getAutoPath();
        AssertUtil.isNotEmpty(ResultCodeConstant.REQUEST_PARAM_ERROR,bucketName,originalFilename);

        String uuidFilePath = autoPath.equals(Boolean.TRUE) ? minioTemplate.generateFileName(originalFilename) : ossFilePath;

        String preSignedUploadUrl = minioTemplate.getPreSignedObjectUrl(bucketName,uuidFilePath);

        String downloadUrl = minioTemplate.getEndPoint() + StrUtil.SLASH + bucketName + uuidFilePath;
        OssFileVO fileVO = OssFileVO.builder()
                .uploadUlr(preSignedUploadUrl)
                .downloadUrl(downloadUrl)
                .build();
        return ResultDTO.getSuccessResult(fileVO);
    }

    @Override
    public void downloadFile(OssDownloadDTO downloadDTO, HttpServletResponse response) {
        AssertUtil.isNotNull(downloadDTO, ResultCodeConstant.REQUEST_PARAM_ERROR);

        String bucketName = downloadDTO.getBucketName();
        String ossFilePath = downloadDTO.getOssFilePath();
        AssertUtil.isNotEmpty(ResultCodeConstant.REQUEST_PARAM_ERROR, bucketName, ossFilePath);

        try {
            InputStream inputStream = minioTemplate.getObjects(bucketName, ossFilePath);
            byte buffer[] = new byte[1024];
            int length;
            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(downloadDTO.getFilename(),"UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = response.getOutputStream();
            // 输出文件
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0, length);
            }
            // 关闭输出流
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            throw new BusinessException(ResultCodeConstant.FILE_DOWNLOAD_ERROR);
        }
    }

    @Override
    public ResultDTO<Boolean> deleteFile(OssDeleteDTO deleteDTO) {
        AssertUtil.isNotNull(deleteDTO,ResultCodeConstant.REQUEST_PARAM_ERROR);

        String bucketName = deleteDTO.getBucketName();
        String ossFilePath = deleteDTO.getOssFilePath();
        AssertUtil.isNotEmpty(ResultCodeConstant.REQUEST_PARAM_ERROR, bucketName, ossFilePath);

        minioTemplate.deleteBucket(bucketName);

        // 将本地oss_file表中的数据status置为DELETE

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }
}
