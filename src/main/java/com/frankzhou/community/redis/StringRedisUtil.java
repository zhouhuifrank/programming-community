package com.frankzhou.community.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 封装StringRedisTemplate对于各种类型数据的操作  每个数据包含存入缓存 不带TTL/带TTL 获取缓存
 * @date 2023-01-15
 */
@Slf4j
@Component
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class StringRedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    // =================通用redis操作====================

    public Boolean setExpire(String key, Long time, TimeUnit unit) {
        try {
            if (time > 0) {
                return stringRedisTemplate.expire(key,time,unit);
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return Boolean.FALSE;
    }

    public Boolean setExpire(String key, Long time) {
        try {
            if (time > 0) {
                return stringRedisTemplate.expire(key,time,TimeUnit.SECONDS);
            }
            return false;
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return false;
    }

    public Long getExpire(String key) {
        try {
            return stringRedisTemplate.getExpire(key);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return null;
    }

    public Boolean hashKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return Boolean.FALSE;
    }

    public Boolean deleteObject(String key) {
        try {
            return stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return Boolean.FALSE;
    }

    public Long deleteObject(Collection<String> collection) {
        try {
            Long count = stringRedisTemplate.delete(collection);
            return count;
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        Long count = stringRedisTemplate.delete(collection);
        return null;
    }

    // =================String类型操作===================

    public <T> void setStringObject(String key,T value) {
        try {
            String json = JSONUtil.toJsonStr(value);
            stringRedisTemplate.opsForValue().set(key,json);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
    }

    public <T> void setStringObject(String key,T value,Long time,TimeUnit unit) {
        try {
            String json = JSONUtil.toJsonStr(value);
            stringRedisTemplate.opsForValue().set(key,json,time,unit);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
    }

    public <T> T getStringObject(String key,Class<T> clazz) {
        try {
            // 不能直接转，需要判断是否存在，否则有NPE
            String json = stringRedisTemplate.opsForValue().get(key);
            T result = BeanUtil.toBean(json, clazz);
            return result;
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return null;
    }

    // =================Hash类型操作======================

    public <T> void setHashObject(String key,T value) {
        try {
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(value, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((k, v) -> v.toString()));
            stringRedisTemplate.opsForHash().putAll(key,stringObjectMap);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
    }

    public <T> void setHashObject(String key,T value,Long time,TimeUnit unit) {
        try {
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(value, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((k, v) -> v.toString()));
            stringRedisTemplate.opsForHash().putAll(key,stringObjectMap);
            stringRedisTemplate.expire(key,time,unit);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
    }

    public <T> T getHashObject(String key,Class<T> clazz) {
        try {
            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
            T target = null;
            try {
                target = clazz.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            T result = BeanUtil.fillBeanWithMap(entries, target, false);
            return result;
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return null;
    }

    // =================List类型操作======================

    public <T> void setListObject(String key,List<T> cacheList) {
        try {
            List<String> stringList = cacheList.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());
            stringRedisTemplate.opsForList().leftPushAll(key,stringList);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
    }

    public <T> void setListObject(String key,List<T> cacheList,Long time,TimeUnit unit) {
        try {
            List<String> stringList = cacheList.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());
            stringRedisTemplate.opsForList().leftPushAll(key,stringList);
            stringRedisTemplate.expire(key,time,unit);
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
    }

    public <T> List<T> getListObject(String key,Class<T> clazz) {
        try {
            List<T> resultList = new ArrayList<>();
            List<String> result = stringRedisTemplate.opsForList().range(key, 0, -1);
            for (String target : result) {
                T t = BeanUtil.toBean(target, clazz);
                resultList.add(t);
            }
            return resultList;
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
        return null;
    }

    // =================Set类型操作=======================

    public <T> void setSetObject(String key,T value) {
        try {
        } catch (Exception e) {
            log.warn("Redis服务异常");
        }
    }

    public <T> T getSetObject(String key,Class<T> clazz) {
        return null;
    }

    // =================SortedSet类型操作=================

    public <T> void setZSetObject(String key,T value) {
        return;
    }

    public <T> T getZSetObject(String key,Class<T> clazz) {
        return null;
    }

}
