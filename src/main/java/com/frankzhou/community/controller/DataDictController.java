package com.frankzhou.community.controller;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.vo.DataDictVO;
import com.frankzhou.community.service.DataDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 数据字典控制器 数据字典的增删改查
 * @date 2023-06-18
 */
@RestController
@RequestMapping("/dataDict")
@Api(tags = {"数据字典"})
public class DataDictController {

    @Resource
    private DataDictService dataDictService;

    @ApiOperation(value = "新增数据字典")
    @PostMapping("/add")
    public ResultDTO<Boolean> addDataDict(@RequestBody DataDictAddDTO addDTO) {
        return dataDictService.insertOne(addDTO);
    }

    @ApiOperation(value = "更新数据字典")
    @PostMapping("/update")
    public ResultDTO<Boolean> updateDataDict(@RequestBody DataDictUpdateDTO updateDTO) {
        return dataDictService.updateById(updateDTO);
    }

    @ApiOperation(value = "单条删除数据字典")
    @PostMapping("/delete")
    public ResultDTO<Boolean> deleteDataDict(@RequestBody DeleteDTO deleteDTO) {
        return dataDictService.deleteById(deleteDTO);
    }

    @ApiOperation(value = "批量删除数据字典")
    @PostMapping("/batch/delete")
    public ResultDTO<Boolean> batchDeleteDataDict(@RequestBody DeleteDTO deleteDTO) {
        return dataDictService.batchDelete(deleteDTO);
    }

    @ApiOperation(value = "批量查询数据字典")
    @PostMapping("/list")
    public ResultDTO<List<DataDictVO>> getDataDictList(@RequestBody DataDictQueryDTO queryDTO) {
        return dataDictService.getDataDictList(queryDTO);
    }

    @ApiOperation(value = "分页查询数据字典")
    @PostMapping("/list/page")
    public PageResultDTO<List<DataDictVO>> getDataDictPage(@RequestBody DataDictQueryDTO queryDTO) {
        return dataDictService.getDataDictPage(queryDTO);
    }

    @ApiOperation(value = "查询数据字典明细")
    @GetMapping("/{dictId}")
    public ResultDTO<DataDictVO> getDataDict(@PathVariable("dictId") Long dictId) {
        return dataDictService.getById(dictId);
    }
}
