package com.frankzhou.community.model.dto.collection;

import com.frankzhou.community.common.base.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹请求类
 * @date 2023-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "用户收藏夹请求类")
public class UserCollectionQueryDTO extends PageQueryDTO implements Serializable {
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

}
