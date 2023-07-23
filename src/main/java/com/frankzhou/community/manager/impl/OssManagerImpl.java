package com.frankzhou.community.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.frankzhou.community.common.base.ResultCodeConstant;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.common.enums.DataStatusEnum;
import com.frankzhou.community.common.util.AssertUtil;
import com.frankzhou.community.manager.OssManager;
import com.frankzhou.community.mapper.OssFileMapper;
import com.frankzhou.community.model.entity.OssFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储sql方法
 * @date 2023-07-23
 */
@Slf4j
@Component
public class OssManagerImpl implements OssManager {

    @Resource
    private OssFileMapper ossFileMapper;

    @Override
    public ResultDTO<Boolean> invalidatedFile(String filePath) {
        AssertUtil.isNotEmpty(filePath, ResultCodeConstant.FILE_PATH_NOT_NULL);

        LambdaQueryWrapper<OssFile> query = new LambdaQueryWrapper<>();
        query.eq(OssFile::getFilePath, filePath)
                .eq(OssFile::getStatus, DataStatusEnum.NORMAL.getCode());
        OssFile ossFile = ossFileMapper.selectOne(query);
        AssertUtil.isNotNull(ossFile, ResultCodeConstant.DB_QUERY_NO_DATA);

        // 逻辑删除
        ossFile.setStatus(DataStatusEnum.DELETED.getCode());
        Integer count = ossFileMapper.updateById(ossFile);
        AssertUtil.isTrue(count == 1, ResultCodeConstant.DB_UPDATE_COUNT_ERROR);
        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> batchInvalidatedFile(List<String> filePathList) {
        AssertUtil.isFalse(CollectionUtil.isEmpty(filePathList), ResultCodeConstant.REQUEST_PARAM_ERROR);

        Integer count = ossFileMapper.batchInvalidate(filePathList);
        AssertUtil.isTrue(count == filePathList.size(), ResultCodeConstant.DB_UPDATE_COUNT_ERROR);
        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> deleteFile(String filePath) {
        AssertUtil.isNotEmpty(filePath, ResultCodeConstant.FILE_PATH_NOT_NULL);

        LambdaQueryWrapper<OssFile> query = new LambdaQueryWrapper<>();
        query.eq(OssFile::getFilePath, filePath)
                .eq(OssFile::getStatus, DataStatusEnum.DELETED.getCode());
        OssFile ossFile = ossFileMapper.selectOne(query);
        AssertUtil.isNotNull(ossFile, ResultCodeConstant.DB_QUERY_NO_DATA);

        // 物理删除，删除status=DELETE的数据
        Integer count = ossFileMapper.deleteById(ossFile);
        AssertUtil.isTrue(count == 1, ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        return null;
    }

    @Override
    public ResultDTO<Boolean> batchDeleteFile(List<String> filePathList) {
        AssertUtil.isFalse(CollectionUtil.isEmpty(filePathList), ResultCodeConstant.REQUEST_PARAM_ERROR);

        Integer count = ossFileMapper.batchDelete(filePathList);
        AssertUtil.isTrue(count == filePathList.size(), ResultCodeConstant.DB_UPDATE_COUNT_ERROR);
        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<OssFile> getExpireFileList() {
        return null;
    }

    @Override
    public ResultDTO<Boolean> insertFile(OssFile ossFile) {
        AssertUtil.isNotNull(ossFile,ResultCodeConstant.REQUEST_PARAM_ERROR);
        AssertUtil.isNotEmpty(ResultCodeConstant.REQUEST_PARAM_ERROR,ossFile.getFilePath());

        Integer count = ossFileMapper.insert(ossFile);
        AssertUtil.isTrue(count == 1, ResultCodeConstant.DB_INSERT_COUNT_ERROR);
        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> updateFile(OssFile ossFile) {
        AssertUtil.isNotNull(ossFile,ResultCodeConstant.REQUEST_PARAM_ERROR);
        AssertUtil.isNotEmpty(ResultCodeConstant.REQUEST_PARAM_ERROR,ossFile.getFilePath());

        LambdaUpdateWrapper<OssFile> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OssFile::getFilePath,ossFile.getFilePath());
        Integer count = ossFileMapper.update(ossFile, updateWrapper);
        AssertUtil.isTrue(count == 1, ResultCodeConstant.DB_INSERT_COUNT_ERROR);

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }
}
