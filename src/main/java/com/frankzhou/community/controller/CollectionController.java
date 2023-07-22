package com.frankzhou.community.controller;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionAddDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionUpdateDTO;
import com.frankzhou.community.model.vo.UserCollectionVO;
import com.frankzhou.community.service.UserCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹控制器
 * @date 2023-06-18
 */
@RestController
@RequestMapping("/collection")
@Api(tags = {"用户收藏夹"})
public class CollectionController {

    @Resource
    private UserCollectionService collectionService;

    @ApiOperation(value = "获取单个收藏夹信息")
    @PostMapping("/{collectionId}")
    public ResultDTO<UserCollectionVO> getCollectionInfo(@PathVariable("collectionId") Long collectionId) {
        return collectionService.getCollectionInfo(collectionId);
    }

    @ApiOperation(value = "新建收藏夹")
    @PostMapping("/add")
    public ResultDTO<Boolean> buildCollection(@RequestBody UserCollectionAddDTO addDTO) {
        return collectionService.buildCollection(addDTO);
    }

    @ApiOperation(value = "编辑收藏夹")
    @PostMapping("/edit")
    public ResultDTO<Boolean> editCollectionInfo(@RequestBody UserCollectionUpdateDTO updateDTO) {
        return collectionService.editCollectionInfo(updateDTO);
    }

    @ApiOperation(value = "删除收藏夹")
    @PostMapping("/delete")
    public ResultDTO<Boolean> deleteCollection(@RequestBody DeleteDTO deleteDTO) {
        return collectionService.deleteCollection(deleteDTO);
    }

    @ApiOperation(value = "查询用户收藏夹")
    @PostMapping("/list")
    public ResultDTO<List<UserCollectionVO>> queryCollectionList(@RequestBody UserCollectionQueryDTO queryDTO) {
        return collectionService.queryCollectionList(queryDTO);
    }

    @ApiOperation(value = "分页查询用户收藏夹")
    @PostMapping("/list/page")
    public PageResultDTO<List<UserCollectionVO>> queryCollectionPage(@RequestBody UserCollectionQueryDTO queryDTO) {
        return collectionService.queryCollectionPage(queryDTO);
    }
}
