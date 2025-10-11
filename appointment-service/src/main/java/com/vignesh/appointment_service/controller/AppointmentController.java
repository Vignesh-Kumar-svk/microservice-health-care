package com.vignesh.appointment_service.controller;


import com.vignesh.appointment_service.dto.AppointmentRequestDto;
import com.vignesh.appointment_service.dto.AppointmentResponseDto;
import com.vignesh.appointment_service.service_impl.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl appointmentService;

    @PostMapping("/booking")
    public ResponseEntity<AppointmentResponseDto> createBooking(@RequestBody AppointmentRequestDto appointmentRequestDto){
        AppointmentResponseDto appointmentResponseDto = appointmentService.bookingAppointment(appointmentRequestDto);
        return ResponseEntity.ok().body(appointmentResponseDto);
    }
}
