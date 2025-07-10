package com.example.demo.controller;

import com.example.demo.service.HomeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;

    final private HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/ver/{token}")
    public boolean verifyToken(@PathVariable String token) {
        return this.homeService.verifyToken(token);
    }

    @GetMapping("/gen")
    public ResponseEntity generateToken() {
        var cookie = ResponseCookie.from("token", this.homeService.generateToken()).path("/").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, String.valueOf(cookie)).body("huy");
    }

    @GetMapping("/redis")
    public String redisToken() {
        this.homeService.testRedis();
        return "hello redis";
    }

    @GetMapping("/home")
    public String home() throws Exception {
        throw new Exception("hello world");
//        return "home";
    }
}
