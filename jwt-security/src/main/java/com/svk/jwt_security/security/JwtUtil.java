package com.svk.jwt_security.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;

import javax.crypto.SecretKey;

public class JwtUtil {


    //To generate Random secret key
    static SecretKey key = Jwts.SIG.HS512.key().build();

    static String base64Key = Encoders.BASE64.encode(key.getEncoded());

    //We can also generate base64Key="Vignesh-Microservice-project" (Note : Secret code to be added in application props)
    private final static SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Key));


    //Expiration time
    private final int jwtTokenExpiry = 300000; //5 mins
}
