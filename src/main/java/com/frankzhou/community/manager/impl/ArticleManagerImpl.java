package com.frankzhou.community.manager.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultCodeConstant;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.common.enums.DataStatusEnum;
import com.frankzhou.community.common.exception.BusinessException;
import com.frankzhou.community.common.util.ListUtil;
import com.frankzhou.community.manager.ArticleManager;
import com.frankzhou.community.mapper.ArticleMapper;
import com.frankzhou.community.model.dto.article.ArticleAddDTO;
import com.frankzhou.community.model.dto.article.ArticleQueryDTO;
import com.frankzhou.community.model.dto.article.ArticleUpdateDTO;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.entity.Article;
import com.frankzhou.community.model.entity.DataDict;
import com.frankzhou.community.model.vo.ArticleVO;
import com.frankzhou.community.model.vo.DataDictVO;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 文章管理 通用方法层实现类
 * @date 2023-06-24
 */
public class ArticleManagerImpl implements ArticleManager {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResultDTO<ArticleVO> getById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId,id)
                .eq(Article::getStatus, DataStatusEnum.NORMAL.getCode());
        Article oneById = articleMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        ArticleVO articleVO = new ArticleVO();
        BeanUtil.copyProperties(oneById,articleVO);

        return ResultDTO.getSuccessResult(articleVO);
    }

    @Override
    public ResultDTO<Boolean> updateById(ArticleUpdateDTO updateDTO) {
        if (ObjectUtil.isNull(updateDTO) || updateDTO.getId() <= 0) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId,updateDTO.getId())
                .eq(Article::getStatus,DataStatusEnum.NORMAL.getCode());
        Article oneById = articleMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        Article article = new Article();
        BeanUtil.copyProperties(updateDTO,article);
        Integer updateCount = articleMapper.updateById(article);
        if (updateCount < 1) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_UPDATE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> deleteById(DeleteDTO deleteDTO) {
        if (ObjectUtil.isNull(deleteDTO) || deleteDTO.getId() <= 0) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId,deleteDTO.getId())
                .eq(Article::getStatus,DataStatusEnum.NORMAL.getCode());
        Article oneById = articleMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        Integer deleteCount = articleMapper.deleteById(deleteDTO.getId());
        if (deleteCount < 1) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> insertOne(ArticleAddDTO addDTO) {
        if (ObjectUtil.isNull(addDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        Article article = new Article();
        BeanUtil.copyProperties(addDTO,article);
        article.setStatus(DataStatusEnum.NORMAL.getCode());
        Integer insertCount = articleMapper.insert(article);
        if (insertCount < 1) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_INSERT_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> batchDelete(DeleteDTO deleteDTO) {
        if (ObjectUtil.isNull(deleteDTO) || StringUtils.isBlank(deleteDTO.getIds())) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        String ids = deleteDTO.getIds();
        String[] idArray = ids.split(",");
        List<Long> idList = new ArrayList<>();
        for (String str : idArray) {
            idList.add(Long.valueOf(str));
        }

        Integer deleteCount = articleMapper.batchDelete(idList);
        if (deleteCount != idList.size()) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<List<ArticleVO>> getListByCond(ArticleQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        List<Article> articleList = articleMapper.queryListByCond(queryDTO);
        List<ArticleVO> articleVOList = new ArrayList<>();
        if (articleList.size() == 0) {
            return ResultDTO.getSuccessResult(articleVOList);
        }

        articleVOList = ListUtil.listConvert(articleList, ArticleVO.class);

        return ResultDTO.getSuccessResult(articleVOList);
    }

    @Override
    public PageResultDTO<List<ArticleVO>> getPageListByCond(ArticleQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)) {
            return PageResultDTO.getErrorPageResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        Integer currPage = queryDTO.getCurrPage();
        Integer pageSize = queryDTO.getPageSize();

        if (ObjectUtil.isNull(currPage)) {
            // 这里必须要替换成开始行数 startRow = (currPage-1)*pageSize
            queryDTO.setCurrPage(1);
        }

        if (ObjectUtil.isNull(pageSize)) {
            queryDTO.setPageSize(10);
        }

        // 防止爬虫
        if (pageSize > 100) {
            throw new BusinessException(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        // 设置行数
        queryDTO.setStartRow((queryDTO.getCurrPage()-1)* queryDTO.getPageSize());
        Integer totalCount = articleMapper.queryPageCount(queryDTO);
        List<ArticleVO> articleVOList = new ArrayList<>();
        if (totalCount == 0) {
            return PageResultDTO.getSuccessPageResult(0,articleVOList);
        }

        List<Article> dataDictList = articleMapper.queryListByPage(queryDTO);
        articleVOList = ListUtil.listConvert(dataDictList, ArticleVO.class);

        return PageResultDTO.getSuccessPageResult(dataDictList.size(),articleVOList);
    }
}
