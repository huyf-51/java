package com.example.demo.service;

public interface HomeService {
    boolean verifyToken(String token);
    String generateToken();
    void testRedis();
}
