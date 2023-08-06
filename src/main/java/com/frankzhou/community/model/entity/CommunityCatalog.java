package com.frankzhou.community.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.frankzhou.community.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 目录表
 * @date 2023-08-06
 */
@TableName(value ="community_catalog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityCatalog extends BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父节点id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 目录名称
     */
    @TableField(value = "catalog_name")
    private String catalogName;

    /**
     * 层级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 路径
     */
    @TableField(value = "tree_path")
    private String treePath;

    /**
     * 排序
     */
    @TableField(value = "sort_num")
    private Integer sortNum;

}