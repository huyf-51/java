package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private LettuceConnectionFactory connectionFactory;

    @Test
    void testRedisConnection() {
        try (RedisConnection connection = connectionFactory.getConnection()) {
            assertNotNull(connection.ping(), "Redis connection test failed");
        }
    }
}
