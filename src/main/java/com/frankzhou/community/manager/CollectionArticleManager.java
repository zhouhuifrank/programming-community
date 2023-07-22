package com.frankzhou.community.manager;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.collection.CollectionArticleDTO;
import com.frankzhou.community.model.dto.collection.CollectionArticleQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.entity.CollectionArticle;
import com.frankzhou.community.model.vo.DataDictVO;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 收藏加文章关联表
 * @date 2023-06-24
 */
public interface CollectionArticleManager {

    ResultDTO<CollectionArticle> getById(Long id);

    ResultDTO<Boolean> updateById(CollectionArticleDTO updateDTO);

    ResultDTO<Boolean> deleteById(DeleteDTO deleteDTO);

    ResultDTO<Boolean> insertOne(CollectionArticleDTO addDTO);

    ResultDTO<Boolean> batchDelete(DeleteDTO deleteDTO);

    ResultDTO<List<CollectionArticle>> getListByCond(CollectionArticleQueryDTO queryDTO);

    PageResultDTO<List<CollectionArticle>> getPageListByCond(CollectionArticleQueryDTO queryDTO);
}
