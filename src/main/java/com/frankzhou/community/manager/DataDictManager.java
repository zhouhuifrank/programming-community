package com.frankzhou.community.manager;


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
 * @description 数据字典 通用方法层
 * @date 2023-06-18
 */
public interface DataDictManager {

    ResultDTO<DataDictVO> getById(Long id);

    ResultDTO<Boolean> updateById(DataDictUpdateDTO updateDTO);

    ResultDTO<Boolean> deleteById(DeleteDTO deleteDTO);

    ResultDTO<Boolean> insertOne(DataDictAddDTO addDTO);

    ResultDTO<Boolean> batchDelete(DeleteDTO deleteDTO);

    ResultDTO<List<DataDictVO>> getListByCond(DataDictQueryDTO queryDTO);

    PageResultDTO<List<DataDictVO>> getPageListByCond(DataDictQueryDTO queryDTO);
}
