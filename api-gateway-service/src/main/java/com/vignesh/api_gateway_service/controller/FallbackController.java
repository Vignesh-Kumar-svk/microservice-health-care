package com.vignesh.api_gateway_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FallbackController {

    @GetMapping("/fallback/appointments")
//    @CircuitBreaker(name = "appointmentSvcCB")
    public ResponseEntity<String> appointmentFallBack(){
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Appointment Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/fallback/patients")
//    @CircuitBreaker(name = "patientSvcCB")
    public ResponseEntity<String> patientFallback() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Patient Service is currently unavailable. Please try again later.");
    }
}
