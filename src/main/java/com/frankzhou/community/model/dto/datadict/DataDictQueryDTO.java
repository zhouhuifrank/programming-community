package com.frankzhou.community.model.dto.datadict;

import com.frankzhou.community.common.base.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 数据字典查询请求类
 * @date 2023-06-18
 */
@Data
@ApiModel(value = "数据字典请求类")
public class DataDictQueryDTO extends PageQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典描述")
    private String dictDesc;

    @ApiModelProperty(value = "参数编码")
    private String paramCode;

    @ApiModelProperty(value = "参数值")
    private String paramValue;

    @ApiModelProperty(value = "参数排序")
    private Integer paramSort;

    private List<Long> idList;

}
