package com.svk.jwt_security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;


@Data
@Entity
public class Roles {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String roleName;

    private String description;
}
