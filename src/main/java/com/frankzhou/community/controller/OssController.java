package com.frankzhou.community.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.frankzhou.community.common.base.ResultCodeConstant;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.common.exception.BusinessException;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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
        return ossService.uploadFile(file);
    }

    @ApiOperation(value = "获取文件上传URL", notes = "前端上传OSS")
    @PostMapping("/upload/url")
    public ResultDTO<OssFileVO> getFileUploadUlr(@RequestBody OssUploadDTO uploadDTO) {
        return ossService.getUploadUrl(uploadDTO);
    }

    @ApiOperation(value = "获取文件", notes = "获取文件")
    @PostMapping("/download")
    public void downloadFile(@RequestBody OssDownloadDTO downloadDTO, HttpServletResponse response) {
        ossService.downloadFile(downloadDTO, response);
    }


    @ApiOperation(value = "删除文件", notes = "删除OSS上的文件")
    @PostMapping("/delete")
    public ResultDTO<Boolean> deleteFile(@RequestBody OssDeleteDTO deleteDTO) {
        return ossService.deleteFile(deleteDTO);
    }

}
