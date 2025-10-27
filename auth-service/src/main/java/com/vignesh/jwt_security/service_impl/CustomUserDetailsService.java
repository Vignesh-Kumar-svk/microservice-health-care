package com.vignesh.jwt_security.service_impl;

import com.vignesh.jwt_security.dto.UserRegisterRequest;
import com.vignesh.jwt_security.entity.Roles;
import com.vignesh.jwt_security.entity.User;
import com.vignesh.jwt_security.repository.RoleRepository;
import com.vignesh.jwt_security.repository.UserRepository;
import com.vignesh.jwt_security.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRegisterRequest userRegisterRequest;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(UserRegisterRequest userRegisterRequest){

        if(userRepository.findByuserName(userRegisterRequest.getUserName()) != null ){
            return null;
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

        return userRepository.save(newUser);
    }

    public String login(User loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                    loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        return jwtUtil.generateToken(loginRequest.getUserName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByuserName(username);

        if(user == null){
            throw new UsernameNotFoundException("Username not found : "+username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.getRoles().
                stream().map(roles -> new SimpleGrantedAuthority(roles.getRoleName())).collect(Collectors.toList()));

    }
}
