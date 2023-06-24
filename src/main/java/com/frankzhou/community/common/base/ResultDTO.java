package com.frankzhou.community.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: this.FrankZhou
 * @date: 2012/12/27
 * @description: 前端通用返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "前端统一返回类")
public class ResultDTO<T> implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * 响应状态码 200表示ok 其他表示异常
     */
    @ApiModelProperty(value = "响应状态码")
    private Integer resultCode;

    /**
     * 错误英文描述
     */
    @ApiModelProperty(value = "错误英文描述")
    private String error;

    /**
     * 错误中文描述
     */
    @ApiModelProperty(value = "错误中文描述")
    private String errorMsg;

    /**
     *  是否请求成功
     */
    @ApiModelProperty(value = "成功标识 true成功/false失败")
    private Boolean isSuccess;

    public static <T> ResultDTO<T> getResult(ResultCodeDTO resultCodeDTO) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setResultCode(resultCodeDTO.getCode());
        result.setError(resultCodeDTO.getMessage());
        result.setErrorMsg(resultCodeDTO.getMessageInfo());
        result.isSuccess = Boolean.FALSE;

        return result;
    }

    public static <T> ResultDTO<T> getSuccessResult() {
        ResultDTO<T> result = new ResultDTO<>();
        result.setResultCode(200);
        result.setIsSuccess(Boolean.TRUE);

        return result;
    }

    public static <T> ResultDTO<T> getSuccessResult(T data) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setResultCode(200);
        result.setData(data);
        result.setIsSuccess(Boolean.TRUE);

        return result;
    }

    public static <T> ResultDTO<T> getErrorResult(ResultCodeDTO codeDTO) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setResultCode(codeDTO.getCode());
        result.setError(codeDTO.getMessage());
        result.setErrorMsg(codeDTO.getMessageInfo());
        result.setIsSuccess(Boolean.FALSE);
        return result;
    }

    public static <T> ResultDTO<T> getErrorResult(Integer code,String error,String errorMsg) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setResultCode(code);
        result.setError(error);
        result.setErrorMsg(errorMsg);
        result.isSuccess = Boolean.FALSE;

        return result;
    }

    private Boolean requestIsSuccess() {
        return this.isSuccess;
    }
}
