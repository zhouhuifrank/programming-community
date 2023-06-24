package com.frankzhou.community.service.impl;

import com.frankzhou.community.common.constant.ErrorConstant;
import com.frankzhou.community.common.exception.BusinessException;
import com.frankzhou.community.service.LockService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description Redisson分布式锁实现类
 * @date 2023-06-18
 */
@Slf4j
@Service
public class LockServiceImpl implements LockService {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public <T> T executeLockWithThrow(String key, long time, TimeUnit unit, SupplierThrow<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean isSuccess = lock.tryLock(time, unit);
        if (!isSuccess) {
            throw new BusinessException(ErrorConstant.LOCK_LIMIT);
        }
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    @Override
    @SneakyThrows
    public <T> T executeLock(String key, long time, TimeUnit unit, SupplierThrow<T> supplier) {
        return executeLockWithThrow(key,time,unit,supplier);
    }
}
