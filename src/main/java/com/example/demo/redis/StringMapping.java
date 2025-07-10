package com.example.demo.redis;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class StringMapping {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public StringMapping(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 600, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
