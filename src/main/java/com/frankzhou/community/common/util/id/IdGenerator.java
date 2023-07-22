package com.frankzhou.community.common.util.id;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description id生成器接口
 * @date 2023-07-02
 */
public interface IdGenerator {

    /**
     * 目前支持四种id生成方式
     * 1.雪花算法 分布式环境下使用
     * 2.时间戳   单库单表
     * 3.随机生成 单库单表
     *
     * @return id
     */
    long nextId();
}
