package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Date;

@Service
public class JWTService {
    private KeyPair keypair;

    public JWTService() {
        SignatureAlgorithm alg = Jwts.SIG.ES512;
        this.keypair = alg.keyPair().build();
    }

    public String generateBearerToken(String subject) {
        return "Bearer" + Jwts.builder()
                .subject(subject)
                .expiration(new Date(System.currentTimeMillis() + 10 * 1000))
                .signWith(keypair.getPrivate())
                .compact();
    }

    public Boolean validateToken(String token) {
        return Jwts.parser()
                .verifyWith(keypair.getPublic())
                .build()
                .parseSignedClaims(this.extractBearerToken(token))
                .getPayload()
                .getSubject() != null; // lam sai roi ne
    }

    public String extractBearerToken(String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2 && parts[0].equalsIgnoreCase("Bearer")) {
            return parts[1];
        } else {
            return null;
        }
    }
}
