package com.frankzhou.community.common.handler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 实现Thread的异常处理器
 * @date 2023-06-11
 */
@Slf4j
public class GlobalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    /**
     * 获取子线程中抛出的异常信息，在主线程中打印出来
     * 可以不在主线程中通过try-catch处理异常，JVM会回调改方法捕获异常
     * 给没有通过try-catch捕获的异常做兜底策略
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in Thread:{} error msg:{}",t.getName(),e.getMessage());
        e.printStackTrace();
    }
}
