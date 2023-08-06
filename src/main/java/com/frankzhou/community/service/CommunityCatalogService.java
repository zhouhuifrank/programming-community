package com.frankzhou.community.service;


import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogAddDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogQueryDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogSwapDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogUpdateDTO;
import com.frankzhou.community.model.vo.CommunityCatalogVO;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 社区目录表业务逻辑层
 * @date 2023-06-18
 */
public interface CommunityCatalogService {

    /**
     * 新增目录
     */
    ResultDTO<Boolean> insertCatalog(CommunityCatalogAddDTO addDTO);

    /**
     * 更新目录
     */
    ResultDTO<Boolean> updateCatalog(CommunityCatalogUpdateDTO updateDTO);

    /**
     * 移除目录
     */
    ResultDTO<Boolean> removeCatalog(DeleteDTO deleteDTO);

    /**
     * 交换目录
     */
    ResultDTO<Boolean> swapCatalog(CommunityCatalogSwapDTO swapDTO);

    /**
     * 查询目录树 全查
     */
    ResultDTO<List<CommunityCatalogVO>> queryCatalogTree();

    /**
     * 根据条件查询目录树
     */
    ResultDTO<List<CommunityCatalogVO>> queryCatalogTreeByCond(CommunityCatalogQueryDTO queryDTO);
}
