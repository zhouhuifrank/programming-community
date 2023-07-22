package com.frankzhou.community.common.util.id;

import com.frankzhou.community.common.enums.IdGeneratorType;
import com.frankzhou.community.common.util.id.policy.RandomNumber;
import com.frankzhou.community.common.util.id.policy.ShortCode;
import com.frankzhou.community.common.util.id.policy.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description id生成器上下文环境
 * @date 2023-07-02
 */
@Slf4j
@Configuration
public class IdContext {

    @Bean
    public Map<String,IdGenerator> idGenerator(SnowFlake snowFlake, ShortCode shortCode, RandomNumber randomNumber) {
        Map<String,IdGenerator> generatorMap = new HashMap<>(8);
        generatorMap.put(IdGeneratorType.SNOW_FLAKE.getCode(),snowFlake);
        generatorMap.put(IdGeneratorType.SHORT_CODE.getCode(),shortCode);
        generatorMap.put(IdGeneratorType.RANDOM_NUMBER.getCode(),randomNumber);
        return generatorMap;
    }
}
