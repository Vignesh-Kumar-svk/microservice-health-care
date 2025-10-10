package com.svk.appointment_service.dto;

import lombok.Data;

@Data
public class AppointmentResponseDto {

    private String patientName;
    private String doctorName;
    private String appointmentDate;
    private String appointmentTime;
    private String message;
    private String bookingStatus;
}
