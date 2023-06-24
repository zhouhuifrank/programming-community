package com.frankzhou.community.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 游标分页查询请求类
 * @date 2023-06-18
 */
@Data
@ApiModel(value = "游标分页请求类")
public class CursorPageQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "游标 初始为null,下次请求时携带过来")
    private String cursor;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize;
}
