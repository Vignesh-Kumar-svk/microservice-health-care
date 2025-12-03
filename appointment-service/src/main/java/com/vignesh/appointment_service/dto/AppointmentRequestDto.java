package com.vignesh.appointment_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentRequestDto {
    private String patientId;
    private String doctorId;
    private String patientEmailId;
    private String appointmentDate;
    private String appointmentTime;
    private String message;

}
