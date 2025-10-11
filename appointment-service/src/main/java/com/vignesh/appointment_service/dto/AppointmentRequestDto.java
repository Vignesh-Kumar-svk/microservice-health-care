package com.vignesh.appointment_service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
public class AppointmentRequestDto {

    private String patientId;
    private String doctorId;
    private String appointmentDate;
    private String appointmentTime;

}
