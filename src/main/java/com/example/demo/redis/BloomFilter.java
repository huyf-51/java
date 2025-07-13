package com.example.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BloomFilter {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public BloomFilter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addBF(String key, String value) {
        redisTemplate.execute((RedisCallback<Void>) connection -> {
            RedisCommands commands = connection.commands();
            commands.execute("BF.ADD", key.getBytes(), value.getBytes());
            return null;
        });
    }

    public Object existsBF(String key, String value) {
        return redisTemplate.execute((RedisCallback<Object>) connection -> {
            RedisCommands commands = connection.commands();
            return commands.execute("BF.EXISTS", key.getBytes(), value.getBytes());
        });
    }
}
