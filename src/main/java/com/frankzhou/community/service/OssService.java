package com.frankzhou.community.service;

import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.oss.OssDeleteDTO;
import com.frankzhou.community.model.dto.oss.OssDownloadDTO;
import com.frankzhou.community.model.dto.oss.OssUploadDTO;
import com.frankzhou.community.model.vo.OssFileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description Oss对象存储服务
 * @date 2023-07-22
 */
public interface OssService {

    ResultDTO<OssFileVO> uploadFile(MultipartFile file);

    ResultDTO<OssFileVO> getUploadUrl(OssUploadDTO uploadDTO);

    void downloadFile(OssDownloadDTO downloadDTO, HttpServletResponse response);

    ResultDTO<Boolean> deleteFile(OssDeleteDTO deleteDTO);
}
