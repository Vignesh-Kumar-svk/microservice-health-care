package com.vignesh.jwt_security.service_impl;

import com.vignesh.jwt_security.entity.User;
import com.vignesh.jwt_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

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
