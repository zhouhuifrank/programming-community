package com.frankzhou.community.aop;

import cn.hutool.extra.servlet.ServletUtil;
import com.frankzhou.community.annotation.RedissonLock;
import com.frankzhou.community.common.util.SpELUtil;
import com.frankzhou.community.service.LockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * @author This.FrankZhou
 * @version 1.0
 * @description Redisson分布式锁切面版本2
 * @date 2023-06-10
 */
@Slf4j
@Aspect
@Component
@Order(0) // 分布式锁切面需要先于@Transcational执行
public class RedissonLockAspect {

    @Resource
    private LockService lockService;

    @Pointcut("@annotation(com.frankzhou.community.annotation.RedissonLock)")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object doRedissonLock(ProceedingJoinPoint jp) throws Throwable {
        Method method = getMethod(jp);
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);

        String prefix = StringUtils.isBlank(redissonLock.prefixKey()) ? SpELUtil.getMethodKey(method) : redissonLock.prefixKey();
        RedissonLock.Target target = redissonLock.target();

        HttpServletRequest request = getRequest();
        String key = "";
        if (target.equals(RedissonLock.Target.IP)) {
            key = ServletUtil.getClientIP(request);
        } else if (target.equals(RedissonLock.Target.UID)) {
            // 从token中获取UID
        } else {
            key = SpELUtil.parseSpEl(method,jp.getArgs(),redissonLock.key());
        }

        String redisKey = prefix + ":" + key;
        return lockService.executeLockWithThrow(redisKey,redissonLock.waitTime(),redissonLock.unit(),jp::proceed);
    }

    private Method getMethod(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return methodSignature.getMethod();
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
