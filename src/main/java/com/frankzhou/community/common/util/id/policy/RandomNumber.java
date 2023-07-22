package com.frankzhou.community.common.util.id.policy;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.frankzhou.community.common.util.id.IdGenerator;
import org.springframework.stereotype.Component;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 随机数生成
 * @date 2023-07-02
 */
@Component
public class RandomNumber implements IdGenerator {

    @Override
    public synchronized long nextId() {
        String idStr = RandomUtil.randomNumbers(32);
        return Long.valueOf(idStr);
    }
}
