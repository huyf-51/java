package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {
    @Test
    void test() {
        assertTrue(true);
        System.out.println("test successful");
    }
}
