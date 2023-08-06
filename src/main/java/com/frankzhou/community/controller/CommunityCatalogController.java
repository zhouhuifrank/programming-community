package com.frankzhou.community.controller;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogAddDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogQueryDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogSwapDTO;
import com.frankzhou.community.model.dto.catalog.CommunityCatalogUpdateDTO;
import com.frankzhou.community.model.vo.CommunityCatalogVO;
import com.frankzhou.community.service.CommunityCatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 社区目录前端控制器
 * @date 2023-08-06
 */
@RestController
@RequestMapping("/catalog")
@Api(tags = {"社区目录管理"})
public class CommunityCatalogController {

    @Resource
    private CommunityCatalogService catalogService;

    @ApiOperation(value = "新增目录")
    @PostMapping("/add")
    public ResultDTO<Boolean> addCatalog(@RequestBody CommunityCatalogAddDTO addDTO) {
        return catalogService.insertCatalog(addDTO);
    }

    @ApiOperation(value = "更新目录")
    @PostMapping("/update")
    public ResultDTO<Boolean> updateCatalog(@RequestBody CommunityCatalogUpdateDTO updateDTO) {
        return catalogService.updateCatalog(updateDTO);
    }

    @ApiOperation(value = "删除目录")
    @PostMapping("/delete")
    public ResultDTO<Boolean> deleteCatalog(@RequestBody DeleteDTO deleteDTO) {
        return catalogService.removeCatalog(deleteDTO);
    }

    @ApiOperation(value = "交换目录树节点")
    @PostMapping("/swap")
    public ResultDTO<Boolean> swapCatalogTreeNode(@RequestBody CommunityCatalogSwapDTO swapDTO) {
        return catalogService.swapCatalog(swapDTO);
    }

    @ApiOperation(value = "查询目录树")
    @GetMapping("/get/tree")
    public ResultDTO<List<CommunityCatalogVO>> queryCatalogTree() {
        return catalogService.queryCatalogTree();
    }

    @ApiOperation(value = "根据条件查询目录树")
    @PostMapping("/get/tree")
    public ResultDTO<List<CommunityCatalogVO>> queryCatalogTreeByCond(@RequestBody CommunityCatalogQueryDTO queryDTO) {
        return catalogService.queryCatalogTreeByCond(queryDTO);
    }

    @ApiOperation(value = "层序遍历查询目录树")
    @GetMapping("/get/level/tree")
    public ResultDTO<List<List<CommunityCatalogVO>>> queryCatalogTreeByLevel() {
        return null;
    }
}
