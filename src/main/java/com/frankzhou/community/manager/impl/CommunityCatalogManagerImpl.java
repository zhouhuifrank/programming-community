package com.frankzhou.community.manager.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.ResultCodeConstant;
import com.frankzhou.community.common.base.ResultCodeDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.common.enums.DataStatusEnum;
import com.frankzhou.community.common.util.AssertUtil;
import com.frankzhou.community.manager.CommunityCatalogManager;
import com.frankzhou.community.mapper.CommunityCatalogMapper;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogAddDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogQueryDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogUpdateDTO;
import com.frankzhou.community.model.entity.CommunityCatalog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 通用方法层
 * @date 2023-08-06
 */
@Slf4j
@Component
public class CommunityCatalogManagerImpl implements CommunityCatalogManager {

    @Resource
    private CommunityCatalogMapper catalogMapper;


    @Override
    public ResultDTO<Boolean> insertCatalog(CommunityCatalogAddDTO insertDTO) {
        if (ObjectUtil.isNotNull(insertDTO)) {
            return ResultDTO.getResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }
        // 非空校验
        String catalogName = insertDTO.getCatalogName();
        Long parentId = insertDTO.getParentId();
        AssertUtil.isNotEmpty(catalogName,
                new ResultCodeDTO(91131, "catalog name is null","目录名不能为空"));
        AssertUtil.isNotNull(parentId,
                new ResultCodeDTO(91132,"parent id is null","父节点id不能为空"));

        // 根节点
        Integer insertCount = null;
        CommunityCatalog catalog = new CommunityCatalog();
        if (ObjectUtil.equal(parentId,0L)) {
            BeanUtil.copyProperties(insertDTO,catalog);
            catalog.setLevel(0);
            catalog.setTreePath(catalogName + "/");
            insertCount = catalogMapper.insert(catalog);
        }
        // 重复性校验
        List<CommunityCatalog> catalogList = this.queryNextCatalogList(parentId);
        Map<String, CommunityCatalog> catalogMap = catalogList.stream()
                .collect(Collectors.toMap(CommunityCatalog::getCatalogName, dto -> dto));
        AssertUtil.isTrue(!catalogMap.containsKey(catalogName),
                new ResultCodeDTO(91132,"catalog name is duplicated","目录名称重复"));
        Integer nextLevel = catalogList.get(0).getLevel();
        String currentTreePath = catalogList.get(0).getTreePath();
        int lastIndex = currentTreePath.lastIndexOf("/");
        String prevTreePath = currentTreePath.substring(0,lastIndex);
        if (ObjectUtil.isNull(insertDTO.getLevel())) {
            insertDTO.setLevel(nextLevel);
        }
        insertDTO.setCatalogName(prevTreePath + catalogName);
        BeanUtil.copyProperties(insertDTO,catalog);
        insertCount = catalogMapper.insert(catalog);
        AssertUtil.isTrue(insertCount == 1,ResultCodeConstant.DB_INSERT_COUNT_ERROR);
        return ResultDTO.getSuccessResult(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> updateCatalog(CommunityCatalogUpdateDTO updateDTO) {
        return null;
    }

    @Override
    public ResultDTO<Boolean> removeCatalog(DeleteDTO deleteDTO) {
        if (ObjectUtil.isNull(deleteDTO)) {
            return ResultDTO.getResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }
        Long id = deleteDTO.getId();

        return null;
    }

    @Override
    public List<CommunityCatalog> queryCatalogList(CommunityCatalogQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)) {
            return new ArrayList<>();
        }
        return null;
    }

    @Override
    public List<CommunityCatalog> queryNextCatalogList(Long parentId) {
        LambdaQueryWrapper<CommunityCatalog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityCatalog::getParentId, parentId)
                .eq(CommunityCatalog::getStatus, DataStatusEnum.NORMAL.getCode());
        return catalogMapper.selectList(wrapper);
    }


    @Override
    public List<CommunityCatalog> queryCatalogLike(Integer level, String treePath) {
        LambdaQueryWrapper<CommunityCatalog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityCatalog::getLevel, level)
                .like(CommunityCatalog::getTreePath, treePath)
                .eq(CommunityCatalog::getStatus, DataStatusEnum.NORMAL.getCode());
        return catalogMapper.selectList(wrapper);
    }

    private List<CommunityCatalog> queryCatalogByLevel(Integer level,Long parentId) {
        LambdaQueryWrapper<CommunityCatalog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityCatalog::getLevel, level)
                .eq(CommunityCatalog::getParentId, parentId)
                .eq(CommunityCatalog::getStatus, DataStatusEnum.NORMAL.getCode());
        return catalogMapper.selectList(wrapper);
    }
}
