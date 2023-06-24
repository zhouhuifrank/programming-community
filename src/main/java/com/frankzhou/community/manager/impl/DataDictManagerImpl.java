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
import com.frankzhou.community.manager.DataDictManager;
import com.frankzhou.community.mapper.DataDictMapper;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.entity.DataDict;
import com.frankzhou.community.model.vo.DataDictVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 数据字典 通用方法层实现类
 * @date 2023-06-18
 */
@Slf4j
@Component
public class DataDictManagerImpl implements DataDictManager {

    @Resource
    private DataDictMapper dataDictMapper;

    @Override
    public ResultDTO<DataDictVO> getById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<DataDict> wrapper = new LambdaQueryWrapper<DataDict>();
        wrapper.eq(DataDict::getId,id)
                .eq(DataDict::getStatus, DataStatusEnum.NORMAL.getCode());
        DataDict oneById = dataDictMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        DataDictVO dataDictVO = new DataDictVO();
        BeanUtil.copyProperties(oneById,dataDictVO);

        return ResultDTO.getSuccessResult(dataDictVO);
    }

    @Override
    public ResultDTO<Boolean> updateById(DataDictUpdateDTO updateDTO) {
        if (ObjectUtil.isNull(updateDTO) || updateDTO.getId() <= 0) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<DataDict> wrapper = new LambdaQueryWrapper<DataDict>();
        wrapper.eq(DataDict::getId,updateDTO.getId())
                .eq(DataDict::getStatus,DataStatusEnum.NORMAL.getCode());
        DataDict oneById = dataDictMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        DataDict dataDict = new DataDict();
        BeanUtil.copyProperties(updateDTO,dataDict);
        Integer updateCount = dataDictMapper.updateById(dataDict);
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

        LambdaQueryWrapper<DataDict> wrapper = new LambdaQueryWrapper<DataDict>();
        wrapper.eq(DataDict::getId,deleteDTO.getId())
                .eq(DataDict::getStatus,DataStatusEnum.NORMAL.getCode());
        DataDict oneById = dataDictMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(oneById)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        Integer deleteCount = dataDictMapper.deleteById(deleteDTO.getId());
        if (deleteCount < 1) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> insertOne(DataDictAddDTO addDTO) {
        if (ObjectUtil.isNull(addDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        DataDict dataDict = new DataDict();
        BeanUtil.copyProperties(addDTO,dataDict);
        dataDict.setStatus(DataStatusEnum.NORMAL.getCode());
        Integer insertCount = dataDictMapper.insert(dataDict);
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

        Integer deleteCount = dataDictMapper.batchDelete(idList);
        if (deleteCount != idList.size()) {
            return ResultDTO.getErrorResult(ResultCodeConstant.DB_DELETE_COUNT_ERROR);
        }

        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<List<DataDictVO>> getListByCond(DataDictQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)) {
            return ResultDTO.getErrorResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        List<DataDict> dataDictList = dataDictMapper.queryListByCond(queryDTO);
        List<DataDictVO> dataDictVOList = new ArrayList<>();
        if (dataDictList.size() == 0) {
            return ResultDTO.getSuccessResult(dataDictVOList);
        }

        dataDictVOList = ListUtil.listConvert(dataDictList, DataDictVO.class);

        return ResultDTO.getSuccessResult(dataDictVOList);
    }

    @Override
    public PageResultDTO<List<DataDictVO>> getPageListByCond(DataDictQueryDTO queryDTO) {
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
        Integer totalCount = dataDictMapper.queryPageCount(queryDTO);
        List<DataDictVO> dataDictVOList = new ArrayList<>();
        if (totalCount == 0) {
            return PageResultDTO.getSuccessPageResult(0,dataDictVOList);
        }

        List<DataDict> dataDictList = dataDictMapper.queryListByPage(queryDTO);
        dataDictVOList = ListUtil.listConvert(dataDictList, DataDictVO.class);

        return PageResultDTO.getSuccessPageResult(dataDictList.size(),dataDictVOList);
    }
}
