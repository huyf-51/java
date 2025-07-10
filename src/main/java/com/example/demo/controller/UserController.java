package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final private UserRepository userRepository;

    final private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/huy")
    public String getUser() {
        return "huy";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        return this.userService.createUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO userDTO) {
        return this.userService.findUser(userDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAllUsers());
    }
}