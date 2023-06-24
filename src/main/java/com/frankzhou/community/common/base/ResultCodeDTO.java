package com.frankzhou.community.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultCodeDTO implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 错误状态码
     */
    private Integer code;

    /**
     * 返回英文信息
     */
    private String message;

    /**
     * 返回中文信息
     */
    private String messageInfo;
}
