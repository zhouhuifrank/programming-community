package com.frankzhou.community.common.util.oss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储文件类
 * @date 2023-07-22
 */
@Data
@AllArgsConstructor
@Builder
public class OssFileInfo {

    private String ossFilePath;

    private String originalFileName;
}
