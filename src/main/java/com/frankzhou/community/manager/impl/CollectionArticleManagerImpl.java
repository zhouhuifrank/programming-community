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
import com.frankzhou.community.manager.CollectionArticleManager;
import com.frankzhou.community.mapper.CollectionArticleMapper;
import com.frankzhou.community.model.dto.collection.CollectionArticleDTO;
import com.frankzhou.community.model.dto.collection.CollectionArticleQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.entity.CollectionArticle;
import com.frankzhou.community.model.entity.DataDict;
import com.frankzhou.community.model.vo.DataDictVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 收藏夹文章关联表
 * @date 2023-06-24
 */
@Slf4j
@Component
public class CollectionArticleManagerImpl implements CollectionArticleManager {

    @Resource
    private CollectionArticleMapper collectionArticleMapper;

    @Override
    public ResultDTO<CollectionArticle> getById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<CollectionArticle> wrapper = new LambdaQueryWrapper<CollectionArticle>();
        wrapper.eq(CollectionArticle::getId,id);
        CollectionArticle oneById = collectionArticleMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        return ResultDTO.getSuccessResult(oneById);
    }

    @Override
    public ResultDTO<Boolean> updateById(CollectionArticleDTO updateDTO) {
        if (ObjectUtil.isNull(updateDTO) || updateDTO.getId() <= 0) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<CollectionArticle> wrapper = new LambdaQueryWrapper<CollectionArticle>();
        wrapper.eq(CollectionArticle::getId,updateDTO.getId());
        CollectionArticle oneById = collectionArticleMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        CollectionArticle collectionArticle = new CollectionArticle();
        BeanUtil.copyProperties(updateDTO,collectionArticle);
        Integer updateCount = collectionArticleMapper.updateById(collectionArticle);
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

        LambdaQueryWrapper<CollectionArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CollectionArticle::getId,deleteDTO.getId());
        CollectionArticle oneById = collectionArticleMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        Integer deleteCount = collectionArticleMapper.deleteById(deleteDTO.getId());
        if (deleteCount < 1) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> insertOne(CollectionArticleDTO addDTO) {
        if (ObjectUtil.isNull(addDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        CollectionArticle collectionArticle = new CollectionArticle();
        BeanUtil.copyProperties(addDTO,collectionArticle);
        Integer insertCount = collectionArticleMapper.insert(collectionArticle);
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

        Integer deleteCount = collectionArticleMapper.batchDelete(idList);
        if (deleteCount != idList.size()) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<List<CollectionArticle>> getListByCond(CollectionArticleQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        List<CollectionArticle> collectionArticleList = collectionArticleMapper.queryListByCond(queryDTO);
        if (collectionArticleList.size() == 0) {
            return ResultDTO.getSuccessResult(new ArrayList<>());
        }

        return ResultDTO.getSuccessResult(collectionArticleList);
    }

    @Override
    public PageResultDTO<List<CollectionArticle>> getPageListByCond(CollectionArticleQueryDTO queryDTO) {
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
        Integer totalCount = collectionArticleMapper.queryPageCount(queryDTO);
        List<CollectionArticle> collectionArticleList = new ArrayList<>();
        if (totalCount == 0) {
            return PageResultDTO.getSuccessPageResult(0,collectionArticleList);
        }

        List<CollectionArticle> dataDictList = collectionArticleMapper.queryListByPage(queryDTO);

        return PageResultDTO.getSuccessPageResult(totalCount,collectionArticleList);
    }
}
