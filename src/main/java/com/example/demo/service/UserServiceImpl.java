package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public boolean verifyToken(String token) {
        return jwtService.validateToken(token);
    }

    public String generateToken() {
        return jwtService.generateBearerToken("test");
    }

    public ResponseEntity<?> createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        String result = passwordEncoder.encode(userDTO.getPassword());

        User user = new User(userDTO.getUsername(), result);

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok("signup ok");
    }

    public ResponseEntity<?> findUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        boolean result = passwordEncoder.matches(userDTO.getPassword(), user.getPassword());

        if (!result) {
            return ResponseEntity.badRequest().body("Wrong password");
        }

        return ResponseEntity.ok(result);
    }
}
