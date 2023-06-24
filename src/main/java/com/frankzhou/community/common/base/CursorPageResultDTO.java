package com.frankzhou.community.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 游标分页返回类
 * @date 2023-06-18
 */
@Data
@NoArgsConstructor
@ApiModel(value = "游标分页返回类")
public class CursorPageResultDTO<T extends List<?>> extends ResultDTO {
    private static final Long serialVersionUID = 1L;

    @ApiModelProperty(value = "游标,下次分页带上该参数")
    private String cursor;

    @ApiModelProperty(value = "分页数据")
    private List<T> pageList;

    public CursorPageResultDTO(String cursor, List<T> pageList, Integer resultCode, String error, String errorMsg) {
        super(null,resultCode,error,errorMsg,true);
        this.cursor = cursor;
        this.pageList = pageList;
    }

    public static <T> CursorPageResultDTO<List<T>> getErrorPageResult(ResultDTO resultDTO) {
        Integer resultCode = resultDTO.getResultCode();
        String error = resultDTO.getError();
        String errorMsg = resultDTO.getErrorMsg();
        return new CursorPageResultDTO<>("",(List)null,resultCode,error,errorMsg);
    }

    public static <T> CursorPageResultDTO<List<T>> getErrorPageResult(ResultCodeDTO codeDTO) {
        return getResult("",(List)null,codeDTO);
    }

    public static <T> CursorPageResultDTO<List<T>> getSuccessPageResult(String cursor, List<T> pageList) {
        return getResult(cursor,pageList,ResultCodeConstant.SUCCESS);
    }

    public static <T> CursorPageResultDTO<List<T>> getResult(String cursor,List<T> pageList,ResultCodeDTO resultCodeDTO) {
        return new CursorPageResultDTO(cursor,pageList,resultCodeDTO.getCode(),resultCodeDTO.getMessage(),resultCodeDTO.getMessageInfo());
    }
}
