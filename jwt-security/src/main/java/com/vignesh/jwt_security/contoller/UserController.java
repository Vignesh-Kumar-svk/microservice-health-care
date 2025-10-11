package com.vignesh.jwt_security.contoller;

import com.vignesh.jwt_security.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    JwtUtil jwtUtil;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value("${role.user}")
    private String roleUser;

    @GetMapping("/protected-api")
    public ResponseEntity<String> getProtectedApi(@RequestHeader("Authorization") String token){

        if(token!=null && token.startsWith("Bearer ")){
            token = token.substring(7);
            if(jwtUtil.isTokenValid(token)){

                String username = jwtUtil.extractUserName(token);
                System.out.println("Username -----------------> "+username);
                Set<String> roles = jwtUtil.extractRoles(token);
                System.out.println("rolesss ------------->"+roles);
                if(roles.contains(roleAdmin)){
                    System.out.println("Welcome user "+username);
                    return ResponseEntity.ok("Welcome "+username + "Roles are :"+roles);
                }
                else if(roles.contains(roleUser)){
                    System.out.println("Welcome user "+username);
                    return ResponseEntity.ok("Welcome "+username + "Roles are :"+roles);
                }
                else{
                    return ResponseEntity.status(403).body("Access Denied : You dont have permission to access");
                }
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid header token is missing");

    }

}
