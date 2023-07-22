package com.frankzhou.community.model.dto.collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹更新类
 * @date 2023-06-24
 */
@Data
@ApiModel(value = "用户收藏夹更新类")
public class UserCollectionUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    private Long id;

    @ApiModelProperty(value = "收藏夹编号")
    private Long collectionId;

    @ApiModelProperty(value = "收藏夹名称")
    private String collectionName;

    @ApiModelProperty(value = "收藏夹描述")
    private String collectionDesc;

    @ApiModelProperty(value = "查看权限")
    private Integer permission;

    @ApiModelProperty(value = "是否默认收藏夹")
    private Integer isDefault;

}
