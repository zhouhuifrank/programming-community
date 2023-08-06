package com.frankzhou.community.model.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 社区目录查询请求类
 * @date 2023-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityCatalogQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String catalogName;

    private Integer level;

    private String treePath;

}
