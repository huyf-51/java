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

@Service
public class Auth extends OncePerRequestFilter {
    private JWTService jwtService;

    @Autowired
    public Auth(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/users") || request.getRequestURI().equals("/api/redis");
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Auth filter");
        try {
            // get token from cookie
//            Cookie[] cookies = request.getCookies();
//            String tokenValue = null;
//            for (Cookie cookie : cookies) {
//                if ("token".equals(cookie.getName())) {
//                    tokenValue = cookie.getValue();
//                    break;
//                }
//            }
            // get token from header
            String tokenValue = request.getHeader("Authorization");
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
