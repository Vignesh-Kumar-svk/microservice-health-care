package com.vignesh.jwt_security.contoller;

import com.vignesh.jwt_security.dto.UserRegisterRequest;
import com.vignesh.jwt_security.entity.User;
import com.vignesh.jwt_security.security.JwtUtil;
import com.vignesh.jwt_security.service_impl.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private CustomUserDetailsService customUserDetailsService;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest userRegisterRequest){
        User status = customUserDetailsService.register(userRegisterRequest);
        if(status==null){
            return ResponseEntity.badRequest().body("UserName is already taken");
        }
        return ResponseEntity.ok("User Registered successfully");
    }



    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User loginRequest){

        String token = customUserDetailsService.login(loginRequest);
        if(token!=null){
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Unauthorized login");
    }

}
