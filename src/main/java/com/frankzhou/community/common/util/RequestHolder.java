package com.frankzhou.community.common.util;

import com.frankzhou.community.common.base.RequestInfo;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 请求信息采集
 * @date 2023-06-18
 */
public class RequestHolder {
    private static final ThreadLocal<RequestInfo> tl = new ThreadLocal<>();

    public static RequestInfo getUser() {
        RequestInfo requestInfo = tl.get();
        return requestInfo;
    }

    public static void setUser(RequestInfo info) {
        tl.set(info);
    }

    public static void remove() {
        tl.remove();
    }
}
