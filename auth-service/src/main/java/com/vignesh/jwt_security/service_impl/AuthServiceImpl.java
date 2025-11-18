package com.vignesh.jwt_security.service_impl;

import com.vignesh.jwt_security.dto.LoginRequestDto;
import com.vignesh.jwt_security.dto.RegisterRequestDto;
import com.vignesh.jwt_security.entity.Roles;
import com.vignesh.jwt_security.entity.User;
import com.vignesh.jwt_security.repository.RoleRepository;
import com.vignesh.jwt_security.repository.UserRepository;
import com.vignesh.jwt_security.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public Optional<String> authenticate(LoginRequestDto loginRequestDto){
        Optional<String> token = userService.findByEmail(loginRequestDto.getEmail())
                .filter(user1 -> passwordEncoder.matches(loginRequestDto.getPassword(),
                                user1.getPassword()))
                .map(user1 -> jwtUtil.generateToken(user1.getEmail(), user1.getRoles()));

        return token;

    }

    public User registerUser(RegisterRequestDto registerRequestDto){

        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setRoles(getUserRoles(registerRequestDto));
        user.setUserName(registerRequestDto.getUserName());
        user.setCreated_at(new Timestamp(System.currentTimeMillis()));
        return userRepository.saveAndFlush(user);

    }

    public Set<Roles> getUserRoles(RegisterRequestDto registerRequestDto){
        Set<Roles> roles = registerRequestDto.getRoles().stream()
                .map(roleName -> {
                    Roles role = roleRepository.findByroleName(roleName);

                    if (role == null) {
                        throw new RuntimeException("Invalid Role: " + roleName);
                    }

                    return role;
                })
                .collect(Collectors.toSet());
        return roles;

    }

    public boolean validateToken(String token){

        try {
            jwtUtil.validateToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }

    }



}
