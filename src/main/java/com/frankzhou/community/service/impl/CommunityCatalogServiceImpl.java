package com.frankzhou.community.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.manager.CommunityCatalogManager;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogAddDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogQueryDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogSwapDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogUpdateDTO;
import com.frankzhou.community.model.entity.CommunityCatalog;
import com.frankzhou.community.model.vo.CommunityCatalogVO;
import com.frankzhou.community.service.CommunityCatalogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 社区目录表 业务逻辑实现类
 * @date 2023-06-18
 */
@Service
public class CommunityCatalogServiceImpl implements CommunityCatalogService {

    @Resource
    private CommunityCatalogManager catalogManager;

    @Override
    public ResultDTO<Boolean> insertCatalog(CommunityCatalogAddDTO addDTO) {
        return null;
    }

    @Override
    public ResultDTO<Boolean> updateCatalog(CommunityCatalogUpdateDTO updateDTO) {
        return null;
    }

    @Override
    public ResultDTO<Boolean> removeCatalog(DeleteDTO deleteDTO) {
        return null;
    }

    @Override
    public ResultDTO<Boolean> swapCatalog(CommunityCatalogSwapDTO swapDTO) {
        return null;
    }

    @Override
    public ResultDTO<List<CommunityCatalogVO>> queryCatalogTree() {
        // 查询根节点
        List<CommunityCatalog> catalogList = catalogManager.queryCatalogLike(0, "root/");
        // 递归组装子节点
        List<CommunityCatalogVO> catalogVOList = this.convertEntityToVO(catalogList);
        buildTreeRecursive(catalogVOList);
        return ResultDTO.getSuccessResult(catalogVOList);
    }

    @Override
    public ResultDTO<List<CommunityCatalogVO>> queryCatalogTreeByCond(CommunityCatalogQueryDTO queryDTO) {
        // 根据条件查询节点


        // 递归组装子节点
        return null;
    }

    /**
     * 前序遍历组装目录树
     */
    private void buildTreeRecursive(List<CommunityCatalogVO> catalogVOList) {
        // 递归终止条件
        if (CollectionUtil.isEmpty(catalogVOList)) {
            return;
        }

        for (CommunityCatalogVO catalogVO : catalogVOList) {
            Long parentId = catalogVO.getParentId();
            if (ObjectUtil.isNotNull(parentId)) {
                List<CommunityCatalog> childList = catalogManager.queryCatalogByParentId(parentId);
                List<CommunityCatalogVO> childVOList = this.convertEntityToVO(childList);
                catalogVO.setChildrenList(childVOList);
                buildTreeRecursive(catalogVOList);
            }
        }
    }

    /**
     * 层序遍历组装目录树
     */
    private List<List<CommunityCatalogVO>> buildTreeLevel(CommunityCatalogVO root) {
        List<List<CommunityCatalogVO>> catalogTree = new ArrayList<>();
        if (ObjectUtil.isNull(root)) {
            return catalogTree;
        }
        // 初始化队列
        Queue<CommunityCatalogVO> catalogVOQueue = new LinkedList<>();
        catalogVOQueue.offer(root);

        while (!catalogVOQueue.isEmpty()) {
            int sz = catalogVOQueue.size();
            List<CommunityCatalogVO> levelList = new ArrayList<>();
            for (int i=0;i<sz;i++) {
                CommunityCatalogVO temp = catalogVOQueue.poll();
                CommunityCatalogQueryDTO queryDTO = new CommunityCatalogQueryDTO();
                BeanUtil.copyProperties(temp,queryDTO);
                List<CommunityCatalog> childList = catalogManager.queryCatalogList(queryDTO);
                // 子树不空
                if (ObjectUtil.isNotNull(childList)) {
                    List<CommunityCatalogVO> childVOList = this.convertEntityToVO(childList);
                    // 添加到队列中
                    catalogVOQueue.addAll(childVOList);
                }
                // 收集该层的树节点
                levelList.add(temp);
            }
            catalogTree.add(levelList);
        }

        return catalogTree;
    }

    private CommunityCatalogVO convertListToTree(List<List<CommunityCatalogVO>> catalogTree) {
        CommunityCatalogVO catalogVO = new CommunityCatalogVO();
        if (ObjectUtil.isNull(catalogTree)) {
            return catalogVO;
        }

        return catalogVO;
    }

    private List<CommunityCatalogVO> convertEntityToVO(List<CommunityCatalog> catalogList) {
        if (CollectionUtil.isEmpty(catalogList)) {
            return new ArrayList<>();
        }

        List<CommunityCatalogVO> catalogVOList = new ArrayList<>();
        for (CommunityCatalog catalog : catalogList) {
            CommunityCatalogVO catalogVO = new CommunityCatalogVO();
            BeanUtil.copyProperties(catalog,catalogVO);
            catalogVOList.add(catalogVO);
        }

        return catalogVOList;
    }
}
