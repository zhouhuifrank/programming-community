package com.frankzhou.community.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: this.FrankZhou
 * @date: 2012/12/27
 * @description: 分页查询结果通用返回类
 */
@Data
@NoArgsConstructor
@ApiModel(value = "分页查询返回类")
public class PageResultDTO<T> extends ResultDTO<T> {
    private static final Long serialVersionUID = 1L;

    @ApiModelProperty(value = "分页数据")
    private List<T> pageList;

    @ApiModelProperty(value = "数据总数")
    private Integer pageCount;

    public PageResultDTO(Integer pageCount, List<T> pageList, Integer resultCode, String error, String errorMsg) {
        super(null,resultCode,error,errorMsg,true);
        this.pageCount = pageCount;
        this.pageList = pageList;
    }

    public static <T> PageResultDTO<List<T>> getErrorPageResult(ResultDTO resultDTO) {
        Integer resultCode = resultDTO.getResultCode();
        String error = resultDTO.getError();
        String errorMsg = resultDTO.getErrorMsg();
        return new PageResultDTO<>(0,(List)null,resultCode,error,errorMsg);
    }

    public static <T> PageResultDTO<List<T>> getErrorPageResult(ResultCodeDTO codeDTO) {
        return getResult(0,(List)null,codeDTO);
    }

    public static <T> PageResultDTO<List<T>> getSuccessPageResult() {
        return getResult(0,new ArrayList<>(0),ResultCodeConstant.SUCCESS);
    }

    public static <T> PageResultDTO<List<T>> getSuccessPageResult(Integer pageCount, List<T> pageList) {
        return getResult(pageCount,pageList,ResultCodeConstant.SUCCESS);
    }

    public static <T> PageResultDTO<List<T>> getResult(Integer pageCount,List<T> pageList,ResultCodeDTO resultCodeDTO) {
        return new PageResultDTO(pageCount,pageList,resultCodeDTO.getCode(),resultCodeDTO.getMessage(),resultCodeDTO.getMessageInfo());
    }

}
