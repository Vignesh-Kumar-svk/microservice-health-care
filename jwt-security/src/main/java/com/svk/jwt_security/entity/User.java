package com.svk.jwt_security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String  email;

    @NotBlank (message = "Encrypted password")
    private String password;

    private Timestamp created_at;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    private Set<Roles> roles ;

}
