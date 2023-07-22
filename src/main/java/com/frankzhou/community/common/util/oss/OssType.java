package com.frankzhou.community.common.util.oss;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储服务类型
 * @date 2023-07-22
 */
@Getter
@AllArgsConstructor
public enum OssType {

    MINIO("minio",1),
    COS("tencent",2),
    OBS("huawei",3),
    ALIBABA("alibaba",4);

    private String name;

    private Integer type;
}
