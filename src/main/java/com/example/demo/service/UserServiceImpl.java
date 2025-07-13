package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.redis.BloomFilter;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final BloomFilter bloomFilter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, BloomFilter bloomFilter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.bloomFilter = bloomFilter;
    }

    public ResponseEntity<?> createUser(UserDTO userDTO) {
        if (this.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        String result = passwordEncoder.encode(userDTO.getPassword());

        User user = new User(userDTO.getUsername(), result);

        User savedUser = userRepository.save(user);
        bloomFilter.addBF("username", savedUser.getUsername());

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
        
        var cookie = ResponseCookie.from("token", this.jwtService.generateToken(user.getId().toString())).path("/").httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, String.valueOf(cookie)).body("login successful");
    }

    public Boolean existsByUsername(String username) {
        Object result = bloomFilter.existsBF("username", username);
        return result.equals(1L);
    }

    public List<String> getAllUsernames() {
        return userRepository.findAll().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
