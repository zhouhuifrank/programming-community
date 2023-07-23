package com.frankzhou.community.mapper;

import com.frankzhou.community.model.entity.OssFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description oss_file表的数据库操作
 * @date 2023-06-18
 */
@Mapper
public interface OssFileMapper extends BaseMapper<OssFile> {

    Integer batchDelete(@Param("list") List<String> filePath);

    Integer batchInvalidate(@Param("list") List<String> filePath);

    List<OssFile> getExpireFileList();
}




