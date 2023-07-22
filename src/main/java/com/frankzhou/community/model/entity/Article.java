package com.frankzhou.community.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.frankzhou.community.common.base.BaseEntity;
import lombok.Data;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 文章实体类
 * @date 2023-06-24
 */
@Data
@TableName(value ="article")
public class Article extends BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 文章id
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 文章概述
     */
    @TableField(value = "brief_description")
    private String briefDescription;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 分类
     */
    @TableField(value = "category")
    private String category;

    /**
     * 标签
     */
    @TableField(value = "tag")
    private String tag;

    /**
     * 关联话题
     */
    @TableField(value = "theme")
    private String theme;

    /**
     * 文章封面url
     */
    @TableField(value = "cover_image_url")
    private String coverImageUrl;

    /**
     * 文章状态
     */
    @TableField(value = "article_status")
    private String articleStatus;

}