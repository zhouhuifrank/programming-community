package com.frankzhou.community.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 社区目录前端返回类
 * @date 2023-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityCatalogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String catalogName;

    private Integer level;

    private String treePath;

    private Integer sortNum;

    // 下一层的目录
    private List<CommunityCatalogVO> childrenList;

    // 下一层的目录
    private Map<String, CommunityCatalogVO> childrenMap;
}
