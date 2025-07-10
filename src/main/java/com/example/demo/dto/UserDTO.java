package com.example.demo.dto;

import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotNull(message = "Username or password is blank")
    @Size(min = 8, message = "username must have least 8 character")
    private String username;

    @NotNull(message = "Username or password is blank")
    @Size(min = 8, message = "password must have least 8 character")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}