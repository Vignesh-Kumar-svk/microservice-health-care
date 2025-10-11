package com.vignesh.patient_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PatientRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 40 , message = "Charater size should be less than 40")
    private String name;

    @NotBlank(message = "Age is required")
    private String age;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String gender;

    @NotBlank(message = "Contact number is required")
    private String contact;

    private String medicalHistory;

    @NotBlank(message = "Registered date is required")
    private String registered_date;
}
