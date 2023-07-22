package com.frankzhou.community.manager;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.article.ArticleAddDTO;
import com.frankzhou.community.model.dto.article.ArticleQueryDTO;
import com.frankzhou.community.model.dto.article.ArticleUpdateDTO;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.vo.ArticleVO;
import com.frankzhou.community.model.vo.DataDictVO;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 文章管理 通用方法层
 * @date 2023-06-24
 */
public interface ArticleManager {

    ResultDTO<ArticleVO> getById(Long id);

    ResultDTO<Boolean> updateById(ArticleUpdateDTO updateDTO);

    ResultDTO<Boolean> deleteById(DeleteDTO deleteDTO);

    ResultDTO<Boolean> insertOne(ArticleAddDTO addDTO);

    ResultDTO<Boolean> batchDelete(DeleteDTO deleteDTO);

    ResultDTO<List<ArticleVO>> getListByCond(ArticleQueryDTO queryDTO);

    PageResultDTO<List<ArticleVO>> getPageListByCond(ArticleQueryDTO queryDTO);
}
