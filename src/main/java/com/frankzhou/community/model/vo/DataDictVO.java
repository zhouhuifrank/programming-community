package com.frankzhou.community.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description
 * @date 2023-06-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDictVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String dictType;

    private String dictDesc;

    private String paramCode;

    private String paramValue;

    private Integer paramSort;

    private String remark;

    private String createUser;

    private String updateUser;
}
