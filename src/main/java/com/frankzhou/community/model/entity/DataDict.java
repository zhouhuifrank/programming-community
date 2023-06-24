package com.frankzhou.community.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.frankzhou.community.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 数据字典表
 * @date 2023-06-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value ="data_dict")
public class DataDict extends BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典类型
     */
    @TableField(value = "dict_type")
    private String dictType;

    /**
     * 字典描述
     */
    @TableField(value = "dict_desc")
    private String dictDesc;

    /**
     * 参数编码
     */
    @TableField(value = "param_code")
    private String paramCode;

    /**
     * 参数值
     */
    @TableField(value = "param_value")
    private String paramValue;

    /**
     * 参数排序
     */
    @TableField(value = "param_sort")
    private Integer paramSort;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建用户
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 更新用户
     */
    @TableField(value = "update_user")
    private String updateUser;

}