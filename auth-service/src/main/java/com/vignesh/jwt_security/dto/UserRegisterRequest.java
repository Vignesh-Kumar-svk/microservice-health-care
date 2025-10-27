package com.vignesh.jwt_security.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRegisterRequest {
    private String userName;
    private String password;
    private String email;
    private Set<String> roles; //As we will pass a set a roles to a user in the request.
                                // Eg: Vignesh - Admin, Engineer, Customer
}
