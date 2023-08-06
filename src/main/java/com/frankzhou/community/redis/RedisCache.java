package com.frankzhou.community.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.frankzhou.community.common.base.ResultCodeDTO;
import com.frankzhou.community.common.exception.BusinessException;
import com.frankzhou.community.config.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 缓存工具类 String类型 互斥锁 设置逻辑过期时间 解决缓存击穿问题 使用函数式编程以及泛型来实现用户的自定义过程
 * @date 2023-01-14
 */
@Slf4j
@Component
public class RedisCache {

    @Resource(name = ThreadPoolConfig.COMMON_EXECUTOR)
    private Executor executor;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 使用String存一个对象
     */
    public <T> void setObject(String key, T value, Long time, TimeUnit unit) {
        try {
            if (ObjectUtil.isNotNull(time)) {
                stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(value),time,unit);
            } else {
                stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(value));
            }
        } catch (Exception e) {
            log.error("redis服务错误");
        }
    }

    /**
     * 设置一个空缓存
     */
    public void setNullCache(String key, Long time, TimeUnit unit) {
        try {
            if (ObjectUtil.isNotNull(time)) {
                stringRedisTemplate.opsForValue().set(key,"",time,unit);
            } else {
                stringRedisTemplate.opsForValue().set(key,"");
            }
        } catch (Exception e) {
            log.error("redis服务错误");
        }
    }

    /**
     * 设置逻辑过期时间
     */
    public <T> void setWithLogicTime(String key, T value, Long time, TimeUnit unit) {
        try {
            RedisData redisData = RedisData.builder()
                    .data(value)
                    .expireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)))
                    .build();
            String logicCache = JSONUtil.toJsonStr(redisData);
            stringRedisTemplate.opsForValue().set(key, logicCache);
        } catch (Exception e) {
            log.error("redis服务错误");
        }
    }

    /**
     * 批量获取缓存
     */
    public <T, R> Map<T, R> getBatchCache(List<T> req,String prefixKey,Class<R> clazz,Function<List<T>,Map<T,R>> dbCallBack, Long time, TimeUnit unit) {
        // 去重后，批量组装key
        req = req.stream().distinct().collect(Collectors.toList());
        List<String> keyList = req.stream().map(key -> prefixKey + key).collect(Collectors.toList());
        List<String> jsonList = stringRedisTemplate.opsForValue().multiGet(keyList);
        // mget命令批量获取缓存
        List<R> valueList = jsonList.stream().map(jsonStr -> BeanUtil.toBean(jsonList, clazz)).collect(Collectors.toList());
        // 计算差值逻辑
        List<T> loadList = new ArrayList<>();
        for (int i=0; i<valueList.size(); i++) {
            // 如果为空，说明缓存不存在
            if (ObjectUtil.isNull(valueList.get(i))) {
                loadList.add(req.get(i));
            }
        }

        // 重新加载不存在的缓存数据
        Map<T, R> queryMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(loadList)) {
            queryMap = dbCallBack.apply(loadList);
            // 组装数据批量存入redis
            Map<String, String> loadMap = new HashMap<>();
            for (Map.Entry<T,R> entry : queryMap.entrySet()) {
                T key = entry.getKey();
                R value = entry.getValue();
                loadMap.put(prefixKey + key,JSONUtil.toJsonStr(value));
            }
            stringRedisTemplate.opsForValue().multiSet(loadMap);
            loadMap.forEach((key, value) -> {
                stringRedisTemplate.expire(key,time,unit);
            });
        }

        // 组装返回数据
        Map<T, R> resultMap = new HashMap<>();
        for (int i=0; i<req.size(); i++) {
            T key = req.get(i);
            // 使用Optional不为空则从valueList取，为空则从queryMap中取
            R value = Optional.ofNullable(valueList.get(i))
                    .orElse(queryMap.get(i));
            resultMap.put(key, value);
        }

        return resultMap;
    }

    /**
     * 批量删除缓存
     */
    public <T> void deleteBatchCache(List<T> req, String prefixKey) {
        req = req.stream().distinct().collect(Collectors.toList());
        List<String> keyList = req.stream().map(key -> prefixKey + key).collect(Collectors.toList());
        stringRedisTemplate.delete(keyList);
    }

    /**
     * 缓存常用模式
     * cacheSide 旁路缓存
     */
    public <T, ID> T queryWithCacheAside(String keyPrefix, ID objectId, T value, Class<T> clazz,
                                         Function<ID, T> dbCallBack, Long time, TimeUnit unit) {
        String redisKey = keyPrefix + objectId;
        String cacheJson = stringRedisTemplate.opsForValue().get(redisKey);
        // 缓存不空
        if (StringUtils.isNotBlank(cacheJson)) {
            T result = JSONUtil.toBean(cacheJson, clazz);
            return result;
        }

        // 缓存为空
        T dbResult = dbCallBack.apply(objectId);
        if (ObjectUtil.isNotNull(dbResult)) {
            throw new BusinessException(new ResultCodeDTO(91132,"cache is not exist",
                    redisKey + "不存在"));
        }
        stringRedisTemplate.opsForValue().set(redisKey,JSONUtil.toJsonStr(dbResult),time,unit);
        return dbResult;
    }

    /**
     * 缓存穿透解决方案
     * 1. 缓存空对象
     * 2. 布隆过滤器BitMap
     */
    public <T, ID> T queryWithPenetrate(String keyPrefix, ID objectId, T value, Class<T> clazz,
                                         Function<ID, T> dbCallBack, Long time, TimeUnit unit) {
        String redisKey = keyPrefix + objectId;
        String cacheJson = stringRedisTemplate.opsForValue().get(redisKey);
        // 缓存不空
        if (StringUtils.isNotBlank(cacheJson)) {
            T result = JSONUtil.toBean(cacheJson, clazz);
            return result;
        }
        // 判断是不是空对象
        if (cacheJson.equals("")) {
            throw new BusinessException(new ResultCodeDTO(91132,"cache is not exist",
                    redisKey + "不存在"));
        }

        T dbResult = dbCallBack.apply(objectId);
        if (ObjectUtil.isNull(dbResult)) {
            // 缓存空对象
            this.setNullCache(redisKey,time,unit);
            throw new BusinessException(new ResultCodeDTO(91132,"cache is not exist",
                    redisKey + "不存在"));
        }
        stringRedisTemplate.opsForValue().set(redisKey,JSONUtil.toJsonStr(dbResult),time,unit);
        return dbResult;
    }

    /**
     * 缓存击穿解决方案
     * 使用互斥锁重建缓存
     */
    public <T, ID> T queryWithMutex(String keyPrefix, ID objectId, T value, Class<T> clazz,
                                     Function<ID, T> dbCallBack, Long time, TimeUnit unit) {
        String redisKey = keyPrefix + objectId;
        String cacheJson = stringRedisTemplate.opsForValue().get(redisKey);
        // 缓存不空
        if (StringUtils.isNotBlank(cacheJson)) {
            T result = JSONUtil.toBean(cacheJson, clazz);
            return result;
        }
        // 判断是不是空对象
        if (cacheJson.equals("")) {
            throw new BusinessException(new ResultCodeDTO(91132,"cache is not exist",
                    redisKey + "不存在"));
        }

        // 获取互斥锁，重建缓存
        String lockKey = keyPrefix + "lock:" + objectId;
        RLock lock = redissonClient.getLock(lockKey);
        T dbResult = null;
        try {
            boolean lockSuccess = lock.tryLock(2, TimeUnit.SECONDS);
            // doubleCheck 是否其他线程已经重建过缓存了
            String doubleCheckCache = stringRedisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotBlank(doubleCheckCache)) {
                // 已经缓存过了，直接返回
                return JSONUtil.toBean(doubleCheckCache,clazz);
            }
            if (!lockSuccess) {
                // 没有获取倒锁，休眠一会进行重试
                Thread.sleep(2000);
                return queryWithMutex(keyPrefix, objectId, value, clazz, dbCallBack, time, unit);
            }
            // 获取到锁了，进行缓存重建
            dbResult = dbCallBack.apply(objectId);
            if (ObjectUtil.isNull(dbResult)) {
                // 缓存空对象
                this.setNullCache(redisKey,time,unit);
                throw new BusinessException(new ResultCodeDTO(91132,"cache is not exist",
                        redisKey + "不存在"));
            }
            stringRedisTemplate.opsForValue().set(redisKey,JSONUtil.toJsonStr(dbResult),time,unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        return dbResult;
    }

    /**
     * 缓存击穿解决方案
     * 设置逻辑过期时间，异步线程缓存重建
     */
    public <T, ID> T queryWithLogicTime(String keyPrefix, ID objectId, T value, Class<T> clazz,
                                         Function<ID, T> dbCallBack, Long time, TimeUnit unit) {
        String redisKey = keyPrefix + objectId;
        String cacheJson = stringRedisTemplate.opsForValue().get(redisKey);
        // 逻辑过期时间缓存永不过期，如果过期了说明不存在
        if (StringUtils.isBlank(cacheJson)) {
            throw new BusinessException(new ResultCodeDTO(91132,"cache is not exist",
                    redisKey + "不存在"));
        }
        // 如果缓存不空，判断逻辑过期时间是否过期
        RedisData redisData = JSONUtil.toBean(cacheJson, RedisData.class);
        Object data = redisData.getData();
        T cacheData = JSONUtil.toBean((JSONObject) data,clazz);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 没有过期直接返回
        if (expireTime.isAfter(LocalDateTime.now())) {
            return cacheData;
        }

        // 过期了，获取互斥锁开启异步线程进行缓存重建
        String lockKey = keyPrefix + "lock:" + objectId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean lockSuccess = lock.tryLock(2, TimeUnit.SECONDS);
            // doubleCheck 是否其他线程已经重建过缓存了
            String doubleCheckCache = stringRedisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotBlank(doubleCheckCache)) {
                // 已经缓存过了，直接返回
                return JSONUtil.toBean(doubleCheckCache,clazz);
            }
            // 缓存重建
            executor.execute(() -> {
                T dbResult = dbCallBack.apply(objectId);
                if (ObjectUtil.isNotNull(dbResult)) {
                    this.setWithLogicTime(redisKey,dbResult,time,unit);
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        // 主线程直接返回缓存数据
        return cacheData;
    }

}
