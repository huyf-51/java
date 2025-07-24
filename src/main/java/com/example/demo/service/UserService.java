package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> createUser(UserDTO userDTO);
    ResponseEntity<?> findUser(UserDTO userDTO);
    Object existsByUsername(String username);
    List<String> getAllUsernames();
}
