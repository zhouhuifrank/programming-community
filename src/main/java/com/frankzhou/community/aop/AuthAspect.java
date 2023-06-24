package com.frankzhou.community.aop;

import com.frankzhou.community.annotation.AuthCheck;
import com.frankzhou.community.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 权限校验切面
 * @date 2023-04-09
 */
@Slf4j
@Aspect
@Component
public class AuthAspect {

    @Pointcut("@annotation(com.frankzhou.community.annotation.AuthCheck)")
    public Object doAuthCheck(ProceedingJoinPoint jp) throws Throwable {
        Method method = getMethod(jp);
        AuthCheck authCheck = method.getAnnotation(AuthCheck.class);

        String mustRole = authCheck.mustRole();
        List<String> anyRole = Arrays.stream(authCheck.anyRole()).collect(Collectors.toList());

        // 优先anyRole
        boolean hashRole = false;
        if (CollectionUtils.isNotEmpty(anyRole)) {
            for (String role : anyRole) {

            }
        }

        if (StringUtils.isNotBlank(mustRole)) {

        }

        if (!hashRole) {
            throw new BusinessException("用户暂无权限");
        }

        return jp.proceed();
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
