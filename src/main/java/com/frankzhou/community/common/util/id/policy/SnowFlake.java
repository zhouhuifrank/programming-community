package com.frankzhou.community.common.util.id.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.frankzhou.community.common.util.id.IdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 雪花算法
 * @date 2023-07-02
 */
@Component
public class SnowFlake implements IdGenerator {

    private Snowflake snowflake;

    @PostConstruct
    public void init() {
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

        workerId = workerId >> 16 & 31;

        long dataCenterId = 1L;
        snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
    }


    @Override
    public synchronized long nextId() {
        return snowflake.nextId();
    }
}
