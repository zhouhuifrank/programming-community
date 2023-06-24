package com.frankzhou.community.common.exception;

import com.frankzhou.community.common.base.ResultCodeDTO;
import com.frankzhou.community.common.base.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 全局异常处理
 * @date 2023-04-21
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResultDTO<?> businessExceptionHandler(BusinessException e) {
        log.warn("business exception: {}",e.getMessage());
        return ResultDTO.getResult(e.getResultCodeDTO());
    }

    @ExceptionHandler(Exception.class)
    public ResultDTO<?> runtimeExceptionHandler(Exception e) {
        log.warn("exception:{}",e.getMessage());
        return ResultDTO.getResult(new ResultCodeDTO(9999,"system exception",e.getMessage()));
    }
}
