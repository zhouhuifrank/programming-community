package com.frankzhou.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.entity.DataDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description data_dict表的数据库操作
 * @date 2023-06-18
 */
@Mapper
public interface DataDictMapper extends BaseMapper<DataDict> {

    Integer batchDelete(@Param("list") List<Long> idList);

    List<DataDict> queryListByCond(DataDictQueryDTO queryDTO);

    List<DataDict> queryListByPage(DataDictQueryDTO queryDTO);

    Integer queryPageCount(DataDictQueryDTO queryDTO);

    Integer batchInsert(@Param("list") List<DataDict> dataDictList);

    Integer batchUpdate(@Param("list") List<DataDict> dataDictList);
}




