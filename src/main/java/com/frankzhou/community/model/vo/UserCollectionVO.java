package com.frankzhou.community.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹前端返回类
 * @date 2023-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCollectionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long collectionId;

    private String collectionName;

    private String collectionDesc;

    private Integer permission;

    private Integer isDefault;

    private Integer subscribeCount;

    private Integer articleCount;
}
