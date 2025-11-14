package com.vignesh.healthcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponseDto {

    private String patientId;
    private String patientName;
    private String email;
    private String age;

}
