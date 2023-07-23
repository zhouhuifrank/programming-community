package com.frankzhou.community.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 对象存储桶枚举类
 * @date 2023-07-23
 */
@Getter
@AllArgsConstructor
public enum OssBucketEnum {

    Test("test","测试",7),
    DOCUMENT("document","文档",365),
    PICTURE("picture","图片",30),
    AUDIO("audio","音频",30),
    VIDEO("video","视频",7),
    FOREVER("forever","永久保留",null),
    OTHER("other","其他",60);

    private String bucketName;

    private String desc;

    private Integer deleteDay;

    public static Integer getDeleteDayByBucket(String bucketName) {
        OssBucketEnum[] values = OssBucketEnum.values();
        for (OssBucketEnum value : values) {
            if (value.bucketName.equals(bucketName)) {
                return value.deleteDay;
            }
        }

        return null;
    }

}
