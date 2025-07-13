package com.example.demo.service;

import com.example.demo.redis.BloomFilter;
import com.example.demo.redis.HashMapping;
import com.example.demo.redis.StringMapping;
import com.example.demo.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    private final JWTService jwtService;
    private final HashMapping hashMapping;
    private final StringMapping stringMapping;
    private final BloomFilter bloomFilter;
    private final UserService userService;

    @Autowired
    public TestServiceImpl(JWTService jwtService, HashMapping hashMapping, StringMapping stringMapping, BloomFilter bloomFilter, UserService userService) {
        this.jwtService = jwtService;
        this.hashMapping = hashMapping;
        this.stringMapping = stringMapping;
        this.bloomFilter = bloomFilter;
        this.userService = userService;
    }

    public boolean verifyToken(String token) {
        return jwtService.validateToken(token);
    }

    public String generateToken() {
        return jwtService.generateToken("test");
    }

    public void testRedis() {
        System.out.println("Testing Redis...");
        this.userService.existsByUsername("huy");
    }
}
