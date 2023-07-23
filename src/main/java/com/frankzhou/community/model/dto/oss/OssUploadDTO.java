package com.frankzhou.community.model.dto.oss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 文件上传请求类
 * @date 2023-07-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OssUploadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * oss存储文件路径
     */
    private String ossFilePath;

    /**
     * 文件原始名称
     */
    private String originalFilename;

    /**
     * 是否自动生成文件路径
     */
    private Boolean autoPath = Boolean.FALSE;
}
