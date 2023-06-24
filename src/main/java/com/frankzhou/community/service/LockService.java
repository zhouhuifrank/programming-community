package com.frankzhou.community.service;

import java.util.concurrent.TimeUnit;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description Redisson分布式锁
 * @date 2023-06-18
 */
public interface LockService {

    <T> T executeLockWithThrow(String key, long time, TimeUnit unit,SupplierThrow<T> supplier) throws Throwable;

    <T> T executeLock(String key, long time, TimeUnit unit, SupplierThrow<T> supplier);

    @FunctionalInterface
    public interface SupplierThrow<T> {
        /**
         * 生产者方法
         */
        T get() throws Throwable;
    }
}
