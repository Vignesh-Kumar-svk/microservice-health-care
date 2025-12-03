package com.vignesh.appointment_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentResponseDto {

    private String patientName;
    private String doctorName;
    private String appointmentDate;
    private String appointmentTime;
    private String message;
    private String bookingStatus;
    private String to;
}
