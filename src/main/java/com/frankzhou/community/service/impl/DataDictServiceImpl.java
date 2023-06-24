package com.frankzhou.community.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultCodeDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.common.enums.DataStatusEnum;
import com.frankzhou.community.manager.DataDictManager;
import com.frankzhou.community.mapper.DataDictMapper;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.entity.DataDict;
import com.frankzhou.community.model.vo.DataDictVO;
import com.frankzhou.community.service.DataDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 数据字典 业务逻辑实现类
 * @date 2023-06-18
 */
@Slf4j
@Service
public class DataDictServiceImpl implements DataDictService {

    @Resource
    private DataDictManager dataDictManager;

    @Resource
    private DataDictMapper dataDictMapper;


    @Override
    public ResultDTO<Boolean> insertOne(DataDictAddDTO addDTO) {
        if (StringUtils.isBlank(addDTO.getDictType())) {
            return ResultDTO.getResult(new ResultCodeDTO(91131,"dict type is null","数据字典类型不能为空"));
        }
        if (StringUtils.isBlank(addDTO.getDictDesc())) {
            return ResultDTO.getResult(new ResultCodeDTO(91132,"dict desc is null","数据字典描述不能为空"));
        }
        if (StringUtils.isBlank(addDTO.getParamCode())) {
            return ResultDTO.getResult(new ResultCodeDTO(91133,"param code is null","参数编码不能为空"));
        }

        // dictType+dictDesc为一个枚举值，同一个枚举内code不能重复
        String dictType = addDTO.getDictType();
        String dictDesc = addDTO.getDictDesc();
        String paramCode = addDTO.getParamCode();
        LambdaQueryWrapper<DataDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DataDict::getDictType,dictType)
                .eq(DataDict::getDictDesc,dictDesc)
                .eq(DataDict::getParamCode,paramCode)
                .eq(DataDict::getStatus, DataStatusEnum.NORMAL.getCode());
        DataDict dataDict = dataDictMapper.selectOne(wrapper);
        if (ObjectUtil.isNotNull(dataDict)) {
            return ResultDTO.getResult(new ResultCodeDTO(91134,"data dict is duplicated","数据字典不能重复"));
        }

        return dataDictManager.insertOne(addDTO);
    }

    @Override
    public ResultDTO<Boolean> updateById(DataDictUpdateDTO updateDTO) {
        if (ObjectUtil.isNull(updateDTO.getId())) {
            return ResultDTO.getResult(new ResultCodeDTO(91130,"id is null","id不能为空"));
        }

        return dataDictManager.updateById(updateDTO);
    }

    @Override
    public ResultDTO<Boolean> deleteById(DeleteDTO deleteDTO) {
        return dataDictManager.deleteById(deleteDTO);
    }

    @Override
    public ResultDTO<Boolean> batchDelete(DeleteDTO deleteDTO) {
        return dataDictManager.batchDelete(deleteDTO);
    }

    @Override
    public ResultDTO<DataDictVO> getById(Long dictId) {
        return dataDictManager.getById(dictId);
    }

    @Override
    public ResultDTO<List<DataDictVO>> getDataDictList(DataDictQueryDTO queryDTO) {
        return dataDictManager.getListByCond(queryDTO);
    }

    @Override
    public PageResultDTO<List<DataDictVO>> getDataDictPage(DataDictQueryDTO queryDTO) {
        return dataDictManager.getPageListByCond(queryDTO);
    }
}




