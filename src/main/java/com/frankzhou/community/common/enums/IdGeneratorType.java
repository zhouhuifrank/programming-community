package com.frankzhou.community.common.enums;

import lombok.Getter;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description id生成器类型
 * @date 2023-07-02
 */
@Getter
public enum IdGeneratorType {

    SNOW_FLAKE("snow_flake","雪花算法"),
    SHORT_CODE("time_code","时间戳短号"),
    RANDOM_NUMBER("random_number","随机数编号");

    private String code;

    private String desc;

    IdGeneratorType(String code,String desc) {
        this.code = code;
        this.desc = desc;
    }
}
