package com.vignesh.jwt_security.util;


import com.vignesh.jwt_security.entity.Roles;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(@Value("${JWT_SECRET}") String secret){
//        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(
//                StandardCharsets.UTF_8));
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, Set<Roles> role){
        return Jwts.builder()
                .subject(email)
                .claim("roles", role.stream().map(Roles::getRoleName).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10Hours
                .signWith(secretKey)
                .compact();

    }

    public void validateToken(String token){
        try {
            Jwts.parser().verifyWith((SecretKey) secretKey).build()
                    .parseSignedClaims(token);
        } catch (JwtException e){
            throw new JwtException("Invaild JWT");
        }
    }
}
