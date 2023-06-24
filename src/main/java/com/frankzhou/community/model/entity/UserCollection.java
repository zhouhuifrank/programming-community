package com.frankzhou.community.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.frankzhou.community.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹实体类
 * @date 2023-06-18
 */
@Data
@TableName(value ="user_collection")
public class UserCollection extends BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收藏夹id
     */
    @TableField(value = "collection_id")
    private Long collectionId;

    /**
     * 收藏夹名称
     */
    @TableField(value = "collection_name")
    private String collectionName;

    /**
     * 收藏夹名称
     */
    @TableField(value = "collection_desc")
    private String collectionDesc;

    /**
     * 查看权限(1公开、2隐私)
     */
    @TableField(value = "permission")
    private Integer permission;

    /**
     * 是否为默认(1默认、0非默认)
     */
    @TableField(value = "is_default")
    private Integer isDefault;

    /**
     * 订阅数量
     */
    @TableField(value = "subscribe_count")
    private Integer subscribeCount;

    /**
     * 文章数量
     */
    @TableField(value = "article_count")
    private Integer articleCount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

}