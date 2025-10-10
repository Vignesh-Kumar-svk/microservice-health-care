package com.svk.appointment_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AppointmentRequestDto {

    private String patientId;
    private String doctorId;
    private String appointmentDate;
    private String appointmentTime;

}
