package com.frankzhou.community.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 数据状态枚举类
 * @date 2023-06-24
 */
@Getter
public enum DataStatusEnum {

    NORMAL("NORMAL","正常"),
    DELETED("DELETED","删除");

    private final String code;

    private final String desc;

    DataStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public List<String> getCodeList() {
        return Arrays.stream(DataStatusEnum.values()).map(DataStatusEnum::getCode).collect(Collectors.toList());
    }

    public List<String> getDescList() {
        return Arrays.stream(DataStatusEnum.values()).map(DataStatusEnum::getDesc).collect(Collectors.toList());
    }

    public static DataStatusEnum getEnumByCode(String code) {
        DataStatusEnum[] values = DataStatusEnum.values();
        for (DataStatusEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }

        return null;
    }

    public static DataStatusEnum getEnumByDesc(String desc) {
        DataStatusEnum[] values = DataStatusEnum.values();
        for (DataStatusEnum value : values) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }

        return null;
    }
}
