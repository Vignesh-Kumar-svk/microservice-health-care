package com.svk.jwt_security.contoller;

import com.svk.jwt_security.dto.UserRegisterRequest;
import com.svk.jwt_security.entity.Roles;
import com.svk.jwt_security.entity.User;
import com.svk.jwt_security.repository.RoleRepository;
import com.svk.jwt_security.repository.UserRepository;
import com.svk.jwt_security.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest userRegisterRequest){

        if(userRepository.findByuserName(userRegisterRequest.getUserName()) != null ){
            return ResponseEntity.badRequest().body("UserName is already taken");
        }

        User newUser = new User();
        newUser.setUserName(userRegisterRequest.getUserName());

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        newUser.setPassword(encodedPassword);

        Set<Roles> roles = new HashSet<>();
        for(String roleName : userRegisterRequest.getRoles()){
            Roles role = roleRepository.findByroleName(roleName);
            if(role==null){
                throw new RuntimeException("Role Not Found : "+roleName);
            }
            roles.add(role);
        }

        newUser.setRoles(roles);
        newUser.setEmail(userRegisterRequest.getEmail());
        newUser.setCreated_at(new Timestamp(System.currentTimeMillis()));

        userRepository.save(newUser);

        return  ResponseEntity.ok("User Registered successfully");

    }



    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User loginRequest){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                    loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        String token = jwtUtil.generateToken(loginRequest.getUserName());
        return ResponseEntity.ok(token);

    }

}
