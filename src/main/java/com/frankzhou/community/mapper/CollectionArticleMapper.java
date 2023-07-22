package com.frankzhou.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankzhou.community.model.dto.collection.CollectionArticleDTO;
import com.frankzhou.community.model.dto.collection.CollectionArticleQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.entity.CollectionArticle;
import com.frankzhou.community.model.entity.DataDict;
import com.frankzhou.community.model.entity.UserCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 针对collection_article表的sql操作
 * @date 2023-06-18
 */
@Mapper
public interface CollectionArticleMapper extends BaseMapper<CollectionArticle> {

    List<CollectionArticle> queryListByCond(CollectionArticleQueryDTO queryDTO);

    List<CollectionArticle> queryListByPage(CollectionArticleQueryDTO queryDTO);

    Integer queryPageCount(CollectionArticleQueryDTO queryDTO);

    Integer batchDelete(@Param("list") List<Long> idList);

    Integer batchInsert(@Param("list") List<CollectionArticle> collectionArticleList);
}




