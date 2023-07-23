package com.frankzhou.community.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.frankzhou.community.common.base.ResultCodeConstant;
import com.frankzhou.community.common.base.ResultCodeDTO;
import com.frankzhou.community.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 参数校验工具类
 * @date 2023-07-22
 */
public class AssertUtil {

    /**
     * 校验到失败就结束
     */
    private static Validator failFastValidator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory().getValidator();

    /**
     * 全部校验
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 注解校验参数(校验到一个参数错误就返回)
     */
    public static <T> void fastValidated(T obj) {
        Set<ConstraintViolation<T>> validateSet = failFastValidator.validate(obj);
        if (validateSet.size() > 0) {
            String errorMsg = validateSet.iterator().next().getMessage();
            throw new BusinessException(errorMsg, ResultCodeConstant.REQUEST_PARAM_ERROR);
        }
    }

    /**
     * 校验所有参数(检验所有参数，统一抛出异常信息)
     */
    public static <T> void allCheckValidated(T obj) {
        Set<ConstraintViolation<T>> validateSet = validator.validate(obj);
        if (validateSet.size() > 0) {
            StringBuffer errorMsgBuffer = new StringBuffer();
            Iterator<ConstraintViolation<T>> iterator = validateSet.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> validation = iterator.next();
                String property = validation.getPropertyPath().toString();
                String message = validation.getMessage();
                errorMsgBuffer.append(property).append(":").append(message).append(",");
            }

            String errorMsg = errorMsgBuffer.substring(0,errorMsgBuffer.length()-1);
            throw new BusinessException(errorMsg,ResultCodeConstant.REQUEST_PARAM_ERROR);
        }
    }

    /**
     * 校验所有参数(检验所有参数，返回异常信息)
     */
    public static <T> Map<String,String> allCheckValidatedMap(T obj) {
        Set<ConstraintViolation<T>> validateSet = validator.validate(obj);
        if (validateSet.size() > 0) {
            Map<String, String> errorMap = new HashMap<>();
            StringBuffer errorMsgBuffer = new StringBuffer();
            Iterator<ConstraintViolation<T>> iterator = validateSet.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> validation = iterator.next();
                errorMap.put(validation.getPropertyPath().toString(), validation.getMessage());
            }

            return errorMap;
        }

        return null;
    }

    /**
     * 如果条件不为True则报错
     */
    public static void isTrue(boolean expression, ResultCodeDTO errorEnum) {
        if (!expression) {
            throw new BusinessException(errorEnum);
        }
    }

    /**
     * 如果条件不为False则报错
     */
    public static void isFalse(boolean expression, ResultCodeDTO errorEnum) {
        if (expression) {
            throw new BusinessException(errorEnum);
        }
    }

    /**
     * 如果为空则报错 单个字符串
     */
    public static void isNotEmpty(String str, ResultCodeDTO errorEnum) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(errorEnum);
        }
    }

    /**
     * 如果任意一个字符串为空则报错 多个字符串
     */
    public static void isNotEmpty(ResultCodeDTO errorEnum, String... css) {
        if (StringUtils.isAnyBlank(css)) {
            throw new BusinessException(errorEnum);
        }
    }

    /**
     * 如果为空则报错
     */
    public static void isNotNull(Object obj, ResultCodeDTO errorEnum) {
        if (ObjectUtil.isNull(obj)) {
            throw new BusinessException(errorEnum);
        }
    }

    /**
     * 如果两个对象不相等则报错
     */
    public static void isEqual(Object obj1, Object obj2, ResultCodeDTO errorEnum) {
        if (!ObjectUtil.equal(obj1,obj2)) {
            throw new BusinessException(errorEnum);
        }
    }

    /**
     * 如果两个对象相等则报错
     */
    public static void isNotEqual(Object obj1, Object obj2, ResultCodeDTO errorEnum) {
        if (ObjectUtil.equal(obj1,obj2)) {
            throw new BusinessException(errorEnum);
        }
    }

}
