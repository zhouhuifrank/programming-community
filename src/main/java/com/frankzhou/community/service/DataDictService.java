package com.frankzhou.community.service;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.vo.DataDictVO;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 数据字典业务逻辑
 * @date 2023-06-18
 */
public interface DataDictService {

    ResultDTO<Boolean> insertOne(DataDictAddDTO addDTO);

    ResultDTO<Boolean> updateById(DataDictUpdateDTO updateDTO);

    ResultDTO<Boolean> deleteById(DeleteDTO deleteDTO);

    ResultDTO<Boolean> batchDelete(DeleteDTO deleteDTO);

    ResultDTO<DataDictVO> getById(Long dictId);

    ResultDTO<List<DataDictVO>> getDataDictList(DataDictQueryDTO queryDTO);

    PageResultDTO<List<DataDictVO>> getDataDictPage(DataDictQueryDTO queryDTO);
}
