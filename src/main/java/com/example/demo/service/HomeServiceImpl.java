package com.example.demo.service;

import com.example.demo.redis.HashMapping;
import com.example.demo.redis.StringMapping;
import com.example.demo.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {
    private final JWTService jwtService;
    private final HashMapping hashMapping;
    private final StringMapping stringMapping;

    @Autowired
    public HomeServiceImpl(JWTService jwtService, HashMapping hashMapping, StringMapping stringMapping) {
        this.jwtService = jwtService;
        this.hashMapping = hashMapping;
        this.stringMapping = stringMapping;
    }

    public boolean verifyToken(String token) {
        return jwtService.validateToken(token);
    }

    public String generateToken() {
        return jwtService.generateToken("test");
    }

    public void testRedis() {
        System.out.println("Testing Redis...");
        System.out.println("String: " + this.hashMapping.loadHash("test"));
    }
}
