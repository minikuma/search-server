package com.search.server.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class RedisRepositoryConfigTest {
    @Autowired
    private RedisRepositoryConfig redisRepositoryConfig;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Test
    void redis_configuration_boot_test() {
        assertNotNull(redisRepositoryConfig.redisConnectionFactory());
    }

    @Test
    void redis_template_test() {
        assertNotNull(redisTemplate.getConnectionFactory());
    }

    @Test
    void 샘플입력_테스트() {
        String key = "검색";
        Integer data = 1;
        ValueOperations<String, Integer> value = redisTemplate.opsForValue();
        value.set(key, data);
        Integer find = value.get(key);
        log.info("find = " + find);
        assertEquals(data, find);
    }
}