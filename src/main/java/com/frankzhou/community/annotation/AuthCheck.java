package com.frankzhou.community.annotation;

import java.lang.annotation.*;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 权限校验注解
 * @date 2023-04-09
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 拥有任意一个权限即可
     */
    String[] anyRole() default "";

    /**
     * 必须拥有某个特定权限
     */
    String mustRole() default "";

    /**
     * 错误信息，可自定义用户无权限时的操作权限
     */
    String errorMsg() default "用户无操作权限";
}
