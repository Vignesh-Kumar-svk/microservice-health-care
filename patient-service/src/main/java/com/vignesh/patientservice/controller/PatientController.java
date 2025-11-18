package com.vignesh.patientservice.controller;

//import com.vignesh.audit_core.annotation.AuditAction;
import com.vignesh.auditcore.aspect.AuditAspect;
import com.vignesh.healthcare.dto.PatientRequestDto;
import com.vignesh.healthcare.dto.PatientResponseDto;
import com.vignesh.patientservice.service_impl.PatientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient" ,description="API for managing  Patients")
public class PatientController {

    @Autowired
    private PatientServiceImpl patientService;


    @PostMapping("/addDetail")
    @Operation(description = "Create Patient ")
//    @AuditAction
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto newPatient = patientService.addPatientDetails(patientRequestDto);
        return ResponseEntity.ok().body(newPatient);
    }

    @Operation(description = "List patients")
    @GetMapping("/listAllPatients")
    public ResponseEntity<List<PatientResponseDto>> listAllPatients(){
        List<PatientResponseDto> allPatients = patientService.listPatientDetails();
        System.out.println("Patient list ----> " + allPatients);
        AuditAspect.logEvent("Patient list fetched");
        return ResponseEntity.ok().body(allPatients);

    }

    @GetMapping("/patient-id/{id}")
    public ResponseEntity<PatientResponseDto> getPatientByPatientId(@PathVariable("id") String patientId){
        PatientResponseDto patientDetail = patientService.getPatientById(patientId);
        return ResponseEntity.ok().body(patientDetail);
    }

//    // Fallback endpoint for circuit breaker
//    @GetMapping("/fallback/patient")
//    public ResponseEntity<String> fallbackPatient() {
//        return ResponseEntity.ok("Patient Service is currently unavailable. Please try again later.");
//    }
}
