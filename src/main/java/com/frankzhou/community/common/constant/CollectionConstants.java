package com.frankzhou.community.common.constant;

import lombok.Getter;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹相关常量
 * @date 2023-07-02
 */
public interface CollectionConstants {

    @Getter
    public enum CollectionPermissionEnum {

        PUBLIC(1,"公开"),
        PRIVATE(2,"隐私");

        private Integer code;

        private String desc;

        CollectionPermissionEnum(Integer code,String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static String getDescByCode(Integer code) {
            CollectionPermissionEnum[] values = CollectionPermissionEnum.values();
            for (CollectionPermissionEnum permissionEnum : values) {
                if (permissionEnum.getCode().equals(code)) {
                    return permissionEnum.getDesc();
                }
            }

            return null;
        }

        public static Integer getCodeByDesc(String desc) {
            CollectionPermissionEnum[] values = CollectionPermissionEnum.values();
            for (CollectionPermissionEnum permissionEnum : values) {
                if (permissionEnum.getDesc().equals(desc)) {
                    return permissionEnum.getCode();
                }
            }

            return null;
        }
    }

    @Getter
    public enum IsDefaultEnum {

        DEFAULT(1,"默认"),
        NOT_DEFAULT(2,"非默认");

        private Integer code;

        private String desc;

        IsDefaultEnum(Integer code,String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static String getDescByCode(Integer code) {
            IsDefaultEnum[] values = IsDefaultEnum.values();
            for (IsDefaultEnum defaultEnum : values) {
                if (defaultEnum.getCode().equals(code)) {
                    return defaultEnum.getDesc();
                }
            }

            return null;
        }

        public static Integer getCodeByDesc(String desc) {
            IsDefaultEnum[] values = IsDefaultEnum.values();
            for (IsDefaultEnum defaultEnum : values) {
                if (defaultEnum.getDesc().equals(desc)) {
                    return defaultEnum.getCode();
                }
            }

            return null;
        }
    }
}
