package com.frankzhou.community.model.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 文章更新请求类
 * @date 2023-06-24
 */
@Data
@ApiModel(value = "文章更新请求类")
public class ArticleUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章概述")
    private String briefDescription;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "文章分类")
    private String category;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "关联话题")
    private String theme;

    @ApiModelProperty(value = "文章封面url")
    private String coverImageUrl;

    @ApiModelProperty(value = "文章状态")
    private String articleStatus;
}
