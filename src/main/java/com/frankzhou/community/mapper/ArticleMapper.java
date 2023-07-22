package com.frankzhou.community.mapper;

import com.frankzhou.community.model.dto.article.ArticleQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankzhou.community.model.entity.DataDict;
import com.frankzhou.community.model.entity.UserCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 针对article表的数据库操作
 * @date 2023-06-24
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> queryListByCond(ArticleQueryDTO queryDTO);

    List<Article> queryListByPage(ArticleQueryDTO queryDTO);

    Integer queryPageCount(ArticleQueryDTO queryDTO);

    Integer batchDelete(@Param("list") List<Long> idList);

    Integer batchInsert(@Param("list") List<Article> articleList);

}




