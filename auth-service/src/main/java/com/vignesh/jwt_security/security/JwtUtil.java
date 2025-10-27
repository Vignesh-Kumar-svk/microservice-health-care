package com.vignesh.jwt_security.security;


import com.vignesh.jwt_security.entity.Roles;
import com.vignesh.jwt_security.entity.User;
import com.vignesh.jwt_security.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {


    //To generate Random secret key
    static SecretKey key = Jwts.SIG.HS512.key().build();

    static String base64Key = Encoders.BASE64.encode(key.getEncoded());

    //We can also generate base64Key="Vignesh-Microservice-project" (Note : Secret code to be added in application props)
    private final static SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Key));


    //Expiration time
    private final int jwtTokenExpiry = 300000; //5 mins


    @Autowired
    private UserRepository userRepository;

    public String generateToken(String  userName){

        User user = userRepository.findByuserName(userName);
        Set<Roles> userRoles = user.getRoles();

        // Convert roles to list of Strings
        String roles = userRoles.stream()
                .map(Roles::getRoleName)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(String.valueOf(user.getUserName()))// ✅ replaces setSubject()
                .claim("roles",roles)
                .issuedAt(new Date())                    // ✅ replaces setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + jwtTokenExpiry))
                .signWith(secretKey)
                .compact();

    }

    //GET CLAIMS
    public String extractUserName (String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Set<String> extractRoles(String token){
        System.out.println(" ----------> "+Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
        String rolesFromToken =  Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("roles", String.class);
        return Collections.singleton(rolesFromToken);

    }

    //Token Validation
    public boolean isTokenValid(String token){
        try{
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }
}
