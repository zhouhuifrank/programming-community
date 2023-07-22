package com.frankzhou.community.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储文件对象返回类
 * @date 2023-07-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OssFileVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String filePath;

    private String fileName;

    private String uploadUlr;

    private String downloadUrl;
}
