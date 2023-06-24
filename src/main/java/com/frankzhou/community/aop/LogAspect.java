package com.frankzhou.community.aop;

import cn.hutool.core.date.StopWatch;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 日志性能监控切面
 * @date 2023-04-09
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(public * com.frankzhou.community.controller.*.*(..))")
    public Object doLogInterceptor(ProceedingJoinPoint jp) throws Throwable {
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 从ThreadLocal中拿到请求信息
        HttpServletRequest request = getRequest();

        // 唯一请求id
        String requestId = UUID.randomUUID().toString();
        String url = request.getRequestURI();
        String clientIP = ServletUtil.getClientIP(request);

        Object[] args = jp.getArgs();
        String requestParams = "{" + StringUtils.join(args,",") + "}";
        log.info("请求id:{} 请求路径:{} 请求ip:{} 请求参数:{}",requestId,
                url,clientIP,requestParams);

        // 执行方法
        Object result = jp.proceed();
        stopWatch.stop();
        long runTime = stopWatch.getTotalTimeMillis();
        log.info("请求id:{} 请求路径:{} 请求ip:{} 方法执行时间:{}ms",requestId,
                url,clientIP,runTime);
        return result;
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
