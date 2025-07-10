package com.example.demo.filter;

import com.example.demo.security.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Service
public class Auth extends OncePerRequestFilter {
    private JWTService jwtService;

    @Autowired
    public Auth(JWTService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Auth filter");
        if (request.getRequestURI().equals("/api/gen") || request.getRequestURI().equals("/api/redis")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Cookie[] cookies = request.getCookies();
            String tokenValue = null;
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    tokenValue = cookie.getValue();
                    break;
                }
            }
            boolean result = this.jwtService.validateToken(tokenValue);
            if (!result) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
