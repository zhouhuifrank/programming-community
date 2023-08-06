package com.frankzhou.community.manager;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogAddDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogQueryDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogUpdateDTO;
import com.frankzhou.community.model.entity.CommunityCatalog;
import com.frankzhou.community.model.vo.CommunityCatalogVO;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 通用方法层
 * @date 2023-08-06
 */
public interface CommunityCatalogManager {

    ResultDTO<Boolean> insertCatalog(CommunityCatalogAddDTO insertDTO);

    ResultDTO<Boolean> updateCatalog(CommunityCatalogUpdateDTO updateDTO);

    ResultDTO<Boolean> removeCatalog(DeleteDTO deleteDTO);

    List<CommunityCatalog> queryCatalogList(CommunityCatalogQueryDTO queryDTO);

    List<CommunityCatalog> queryNextCatalogList(Long parentId);

    List<CommunityCatalog> queryCatalogLike(Integer level,String treePath);
}
