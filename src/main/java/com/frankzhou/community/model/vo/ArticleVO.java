package com.frankzhou.community.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 文章前端返回类
 * @date 2023-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long articleId;

    private String title;

    private String briefDescription;

    private String content;

    private String category;

    private String tag;

    private String theme;

    private String coverImageUrl;

    private String articleStatus;

    private Integer likeCount;

    private Integer visitCount;

    private Integer commentCount;

    private Integer collectCount;
}
