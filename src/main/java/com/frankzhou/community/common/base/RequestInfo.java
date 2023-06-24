package com.frankzhou.community.common.base;

import lombok.Data;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 请求信息收集类
 * @date 2023-06-18
 */
@Data
public class RequestInfo {

    private Long user_id;

    private String ip;
}
