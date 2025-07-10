package com.example.demo.redis;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HashMapping {
    private final RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    HashOperations<String, String, String> hashOperations;

    @Autowired
    public HashMapping(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void writeHash(String key, Map<String, String> hash) {
        Map<String, String> loadedHash = hashOperations.entries(key);
        if (loadedHash.isEmpty()) {
            hashOperations.putAll(key, hash);
        } else {
            loadedHash.putAll(hash);
        }
        this.redisTemplate.expire(key, 600, TimeUnit.SECONDS);
    }

    public Map<String, String> loadHash(String key) {
        return hashOperations.entries(key);
    }
}
