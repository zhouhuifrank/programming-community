package com.frankzhou.community.model.dto.oss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储文件下载请求类
 * @date 2023-07-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OssDownloadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String bucketName;

    private String ossFilePath;

    private String filename;
}
