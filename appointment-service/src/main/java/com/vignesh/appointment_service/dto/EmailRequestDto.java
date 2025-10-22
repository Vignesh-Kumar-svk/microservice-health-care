package com.vignesh.appointment_service.dto;

import lombok.Data;

@Data
public class EmailRequestDto {
    private String to;
    private String subject;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String message;
}
