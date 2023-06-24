package com.frankzhou.community.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 实体类基础属性
 * @date 2023-06-18
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID= 1L;

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

    /**
     * 状态(NORMAL正常、DELETED删除)
     */
    @TableField(value = "status")
    private String status;
}
