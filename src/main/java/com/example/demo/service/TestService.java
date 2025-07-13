package com.example.demo.service;

public interface TestService {
    boolean verifyToken(String token);
    String generateToken();
    void testRedis();
}
