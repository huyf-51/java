package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.security.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTService {
    private KeyPair keypair;

    public JWTService(@Value("file:key/es512-private.pem") Resource privateKeyFile, @Value("file:key/es512-public.pem") Resource publicKeyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        SignatureAlgorithm alg = Jwts.SIG.ES512; // C2: use alg.keyPair() to generate a keypair
//        this.keypair = alg.keyPair().build();

        this.keypair = new KeyPair(loadPublicKey(publicKeyFile), loadPrivateKey(privateKeyFile));
    }

    public PrivateKey loadPrivateKey(Resource privateKeyResource
    ) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String key = Files.readString(privateKeyResource.getFile().toPath(), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);

        return keyFactory.generatePrivate(keySpec);
    }

    public PublicKey loadPublicKey(Resource publicKeyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String key = Files.readString(publicKeyFile.getFile().toPath(), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
    }

    public String generateToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .expiration(new Date(System.currentTimeMillis() + 60 * 1000))
                .signWith(keypair.getPrivate())
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(keypair.getPublic())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}