package com.frankzhou.community.common.util.id.policy;

import com.frankzhou.community.common.util.id.IdGenerator;
import com.frankzhou.community.redis.RedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 日期生成短号
 * @date 2023-07-02
 */
@Component
public class ShortCode implements IdGenerator {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public synchronized long nextId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String nowDate = sdf.format(now);
        Long incrNumber = stringRedisTemplate.opsForValue().increment(RedisKeys.TIME_STAMP_KEY);
        incrNumber = incrNumber & 31;
        Long id = (Long.valueOf(nowDate) << 6) | incrNumber;
        return id;
    }
}
