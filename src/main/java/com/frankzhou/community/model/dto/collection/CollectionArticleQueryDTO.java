package com.frankzhou.community.model.dto.collection;

import com.frankzhou.community.common.base.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 收藏夹文章分页请求类
 * @date 2023-07-02
 */
@Data
@ApiModel(value = "收藏夹文章分页请求类")
public class CollectionArticleQueryDTO extends PageQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏夹id")
    private Long collectionId;

    @ApiModelProperty(value = "文章id")
    private Long articleId;
}
