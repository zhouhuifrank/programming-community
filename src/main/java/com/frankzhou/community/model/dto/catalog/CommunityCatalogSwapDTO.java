package com.frankzhou.community.model.dto.catalog;

import com.frankzhou.community.model.entity.CommunityCatalog;
import com.frankzhou.community.model.vo.CommunityCatalogVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 文件目录交换请求类
 * @date 2023-08-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityCatalogSwapDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private CommunityCatalogVO sourceCatalog;

    private CommunityCatalogVO targetCatalog;

}
