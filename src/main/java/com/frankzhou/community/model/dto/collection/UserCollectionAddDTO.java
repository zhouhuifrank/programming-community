package com.frankzhou.community.model.dto.collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 收藏夹新增请求类
 * @date 2023-06-24
 */
@Data
@ApiModel(value = "收藏夹新增请求类")
public class UserCollectionAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建用户id")
    private Long userId;

    @ApiModelProperty(value = "收藏夹id")
    private Long collectionId;

    @ApiModelProperty(value = "收藏夹名称")
    private String collectionName;

    @ApiModelProperty(value = "收藏夹描述")
    private String collectionDesc;

    @ApiModelProperty(value = "查看权限")
    private Integer permission;

    @ApiModelProperty(value = "是否默认收藏夹")
    private Integer isDefault;

    @ApiModelProperty(value = "收藏夹订阅数量")
    private Integer subscribeCount;

    @ApiModelProperty(value = "收藏夹文章数量")
    private Integer articleCount;

}
