package com.frankzhou.community.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 分页查找请求类 需要分页查找时继承即可
 * @date 2023-04-08
 */
@Data
@ApiModel(value = "分页前端请求类")
public class PageQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页数(前端)
     */
    @ApiModelProperty(value = "当前页码")
    private Integer currPage;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    /**
     * 开始行
     */
    @ApiModelProperty(value = "开始行")
    private Integer startRow;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String orderBy;

    /**
     * 顺序 asc升序/desc降序
     */
    @ApiModelProperty(value = "顺序 asc升序/desc降序")
    private String sort;

    /**
     * 获取当前页面偏移量
     */
    private Integer getOffset() {
        Integer offset = (currPage-1) * pageSize;
        return offset;
    }
}
