package com.vignesh.patient_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PatientResponseDto {

    private String name;
    private String email;
    private String age;

}
