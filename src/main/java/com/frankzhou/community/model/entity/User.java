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
 * @description 用户表
 * @date 2023-06-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value ="user")
public class User extends BaseEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID= 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 性别(0男、1女)
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 邮件
     */
    @TableField(value = "email")
    private String email;

    /**
     * 微信openid
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 自我介绍
     */
    @TableField(value = "self_introduce")
    private String selfIntroduce;

    /**
     * 注册激活码
     */
    @TableField(value = "active_code")
    private String activeCode;

    /**
     * 激活状态(0已注册未激活、1注册并激活)
     */
    @TableField(value = "active_status")
    private Integer activeStatus;

}