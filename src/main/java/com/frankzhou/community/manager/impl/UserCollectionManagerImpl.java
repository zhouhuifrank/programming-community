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
import com.frankzhou.community.manager.UserCollectionManager;
import com.frankzhou.community.mapper.UserCollectionMapper;
import com.frankzhou.community.model.dto.collection.UserCollectionAddDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionUpdateDTO;
import com.frankzhou.community.model.entity.UserCollection;
import com.frankzhou.community.model.vo.UserCollectionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description
 * @date 2023-06-24
 */
@Slf4j
@Component
public class UserCollectionManagerImpl implements UserCollectionManager {

    @Resource
    private UserCollectionMapper collectionMapper;

    @Override
    public ResultDTO<UserCollectionVO> getById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<UserCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollection::getId,id)
                .eq(UserCollection::getStatus, DataStatusEnum.NORMAL.getCode());
        UserCollection oneById = collectionMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        UserCollectionVO collectionVO = new UserCollectionVO();
        BeanUtil.copyProperties(oneById,collectionVO);

        return ResultDTO.getSuccessResult(collectionVO);
    }

    @Override
    public ResultDTO<Boolean> updateById(UserCollectionUpdateDTO updateDTO) {
        if (ObjectUtil.isNull(updateDTO) || updateDTO.getId() <= 0) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<UserCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollection::getId,updateDTO.getId())
                .eq(UserCollection::getStatus,DataStatusEnum.NORMAL.getCode());
        UserCollection oneById = collectionMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        UserCollection userCollection = new UserCollection();
        BeanUtil.copyProperties(updateDTO,userCollection);
        Integer updateCount = collectionMapper.updateById(userCollection);
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

        LambdaQueryWrapper<UserCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollection::getId,deleteDTO.getId())
                .eq(UserCollection::getStatus,DataStatusEnum.NORMAL.getCode());
        UserCollection oneById = collectionMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        Integer deleteCount = collectionMapper.deleteById(deleteDTO.getId());
        if (deleteCount < 1) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> insertOne(UserCollectionAddDTO addDTO) {
        if (ObjectUtil.isNull(addDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        UserCollection userCollection = new UserCollection();
        BeanUtil.copyProperties(addDTO,userCollection);
        userCollection.setStatus(DataStatusEnum.NORMAL.getCode());
        Integer insertCount = collectionMapper.insert(userCollection);
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

        Integer deleteCount = collectionMapper.batchDelete(idList);
        if (deleteCount != idList.size()) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<List<UserCollectionVO>> getListByCond(UserCollectionQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        List<UserCollection> collectionList = collectionMapper.queryListByCond(queryDTO);
        List<UserCollectionVO> collectionVOList = new ArrayList<>();
        if (collectionList.size() == 0) {
            return ResultDTO.getSuccessResult(collectionVOList);
        }

        collectionVOList = ListUtil.listConvert(collectionList, UserCollectionVO.class);

        return ResultDTO.getSuccessResult(collectionVOList);
    }

    @Override
    public PageResultDTO<List<UserCollectionVO>> getPageListByCond(UserCollectionQueryDTO queryDTO) {
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
        Integer totalCount = collectionMapper.queryPageCount(queryDTO);
        List<UserCollectionVO> collectionVOList = new ArrayList<>();
        if (totalCount == 0) {
            return PageResultDTO.getSuccessPageResult(0,collectionVOList);
        }

        List<UserCollection> collectionList = collectionMapper.queryListByPage(queryDTO);
        collectionVOList = ListUtil.listConvert(collectionList, UserCollectionVO.class);

        return PageResultDTO.getSuccessPageResult(totalCount,collectionVOList);
    }

    @Override
    public List<UserCollection> getCollectionByUserId(Long userId) {
        if (ObjectUtil.isNull(userId)) {
            throw new BusinessException("用户id不能为空");
        }

        LambdaQueryWrapper<UserCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollection::getUserId,userId)
                .eq(UserCollection::getStatus,DataStatusEnum.NORMAL.getCode());
        List<UserCollection> collectionList = collectionMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(collectionList)) {
            return new ArrayList<UserCollection>();
        }

        return collectionList;
    }

    @Override
    public UserCollection getCollectionByUnique(Long collectionId) {
        LambdaQueryWrapper<UserCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollection::getCollectionId,collectionId)
                .eq(UserCollection::getStatus,DataStatusEnum.NORMAL.getCode());
        UserCollection userCollection = collectionMapper.selectOne(wrapper);
        return userCollection;
    }

    @Override
    public boolean checkUserCollectionDuplicate(Long userId, String collectionName) {
        LambdaQueryWrapper<UserCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollection::getUserId,userId)
                .eq(UserCollection::getCollectionName,collectionName)
                .eq(UserCollection::getStatus,DataStatusEnum.NORMAL.getCode());
        List<UserCollection> collectionList = collectionMapper.selectList(wrapper);
        if (ObjectUtil.isNotNull(collectionList) || collectionList.size() > 0) {
            return false;
        }

        return true;
    }


}
