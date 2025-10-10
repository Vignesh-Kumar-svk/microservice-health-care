package com.svk.appointment_service.controller;


import com.svk.appointment_service.dto.AppointmentRequestDto;
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
    AppointmentRequestDto appointmentRequestDto;

    @PostMapping("/booking")
    public ResponseEntity<String> createBooking(@RequestBody AppointmentRequestDto appointmentRequestDto){

    }
}
