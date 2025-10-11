package com.vignesh.appointment_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID appointmentId;

    @NotBlank
    private String patientId;

    @NotBlank
    private String doctorId;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    private String reason;

    @NotBlank
    private LocalDate appointmentDate;

    @NotBlank
    private LocalTime appointmentTime;

}


