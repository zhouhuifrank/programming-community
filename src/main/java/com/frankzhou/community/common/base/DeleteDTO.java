package com.frankzhou.community.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 删除请求类
 * @date 2023-04-08
 */
@Data
@ApiModel(value = "删除请求类")
public class DeleteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单条数据删除
     */
    @ApiModelProperty(value = "数据的主键id,单条数据删除")
    private Long id;

    /**
     * 多条数据删除 以逗号隔开
     */
    @ApiModelProperty(value = "多个数据的主键id组合,多条数据删除")
    private String ids;
}
