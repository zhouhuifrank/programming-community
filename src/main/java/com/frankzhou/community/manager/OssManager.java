package com.frankzhou.community.manager;

import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.entity.OssFile;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储sql方法
 * @date 2023-07-23
 */
public interface OssManager {

    ResultDTO<Boolean> invalidatedFile(String filePath);

    ResultDTO<Boolean> batchInvalidatedFile(List<String> filePathList);

    ResultDTO<Boolean> deleteFile(String filePath);

    ResultDTO<Boolean> batchDeleteFile(List<String> filePathList);

    ResultDTO<OssFile> getExpireFileList();

    ResultDTO<Boolean> insertFile(OssFile ossFile);

    ResultDTO<Boolean> updateFile(OssFile ossFile);
}
