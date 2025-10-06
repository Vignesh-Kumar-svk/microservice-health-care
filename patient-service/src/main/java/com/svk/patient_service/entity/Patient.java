package com.svk.patient_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="patients")
public class Patient{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID patientId;

    @Column(name="name")
    @NotNull
    private String name;

    @NotNull
    private int age;

    @Column(unique = true)
    @Email
    private String email;

    @NotNull
    private String  gender;

    @Column(unique = true)
    @NotNull
    private String contact;

    @Column(name="medical_history")
    private String  medicalHistory;

    @NotNull
    private LocalDate registered_date;

}