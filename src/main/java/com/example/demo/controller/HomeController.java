package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;

    final private UserService userService;

    @Autowired
    public HomeController(UserService UserService) {
        this.userService = UserService;
    }

    @GetMapping("/ver/{token}")
    public boolean verifyToken(@PathVariable String token) {
        return this.userService.verifyToken(token);
    }

    @GetMapping("/gen")
    public String generateToken() {
        return this.userService.generateToken();
    }
}
