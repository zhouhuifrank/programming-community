package com.frankzhou.community.model.dto.collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 收藏夹文章关系请求类
 * @date 2023-06-24
 */
@Data
@ApiModel(value = "收藏夹文章关系请求类")
public class CollectionArticleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    private Long id;

    @ApiModelProperty(value = "收藏夹id")
    private Long collectionId;

    @ApiModelProperty(value = "文章id")
    private Long articleId;

}
