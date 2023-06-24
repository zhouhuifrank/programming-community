package com.frankzhou.community.common.exception;

import com.frankzhou.community.common.base.ResultCodeDTO;
import lombok.Data;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 自定义异常类
 * @date 2023-04-21
 */
@Data
public class BusinessException extends RuntimeException {

    private ResultCodeDTO resultCodeDTO;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResultCodeDTO codeDTO) {
        this.resultCodeDTO = codeDTO;
    }

    public BusinessException(String message,ResultCodeDTO codeDTO) {
        super(message);
        this.resultCodeDTO = codeDTO;
    }

}
