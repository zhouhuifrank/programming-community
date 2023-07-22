package com.frankzhou.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.entity.DataDict;
import com.frankzhou.community.model.entity.UserCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 针对user_collection表的sql操作
 * @date 2023-06-18
 */
@Mapper
public interface UserCollectionMapper extends BaseMapper<UserCollection> {

    List<UserCollection> queryListByCond(UserCollectionQueryDTO queryDTO);

    List<UserCollection> queryListByPage(UserCollectionQueryDTO queryDTO);

    Integer queryPageCount(UserCollectionQueryDTO queryDTO);

    Integer batchDelete(@Param("list") List<Long> idList);

    Integer batchInsert(@Param("list") List<UserCollection> collectionList);

}




