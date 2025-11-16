package com.vignesh.jwt_security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    @NotBlank
    private String userName;

    @Email
    @Column(unique = true, nullable = false)
    private String  email;

    @NotBlank (message = "Encrypted password")
    private String password;

    private Timestamp created_at;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    private Set<Roles> roles ;

}
