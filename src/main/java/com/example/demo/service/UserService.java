package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    boolean verifyToken(String token);
    String generateToken();
    ResponseEntity<?> createUser(UserDTO userDTO);
    ResponseEntity<?> findUser(UserDTO userDTO);
}
