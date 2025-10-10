package com.svk.patient_service.controller;

import com.svk.patient_service.dto.PatientRequestDto;
import com.svk.patient_service.dto.PatientResponseDto;
import com.svk.patient_service.service_impl.PatientServiceImpl;
import com.svk.patient_service.entity.Patient;
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
    PatientServiceImpl patientService;


    @PostMapping("/addDetail")
    @Operation(description = "Create Patient ")
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto newPatient = patientService.addPatientDetails(patientRequestDto);
        return ResponseEntity.ok().body(newPatient);
    }

    @Operation(description = "List patients")
    @GetMapping("/listAllPatients")
    public ResponseEntity<List<PatientResponseDto>> listAllPatients(){
        List<PatientResponseDto> allPatients = patientService.listPatientDetails();
        System.out.println("Patient list ----> " + allPatients);
        return ResponseEntity.ok().body(allPatients);

    }
}
