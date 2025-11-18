package com.vignesh.jwt_security.contoller;


import com.vignesh.jwt_security.dto.LoginRequestDto;
import com.vignesh.jwt_security.dto.LoginResponseDto;
import com.vignesh.jwt_security.dto.RegisterRequestDto;
import com.vignesh.jwt_security.dto.RegisterResponseDto;
import com.vignesh.jwt_security.entity.User;
import com.vignesh.jwt_security.service_impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){

        Optional<String> token =  authService.authenticate(loginRequestDto);
        if(token.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new LoginResponseDto(token.get()));
    }

    @Operation(description = "Register User")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDto registerRequestDto){
        User user = authService.registerUser(registerRequestDto);
        if(user!=null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.ok(user);
    }

    @Operation(description = "Validate Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader ("Authorization") String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }


}
