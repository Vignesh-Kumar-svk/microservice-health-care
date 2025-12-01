package com.vignesh.patientservice.controller;

//import com.vignesh.audit_core.annotation.AuditAction;
import com.vignesh.auditcore.aspect.AuditAspect;
import com.vignesh.healthcare.dto.PatientRequestDto;
import com.vignesh.healthcare.dto.PatientResponseDto;
import com.vignesh.patientservice.service_impl.PatientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient" ,description="Endpoints to manage patient registration and retrieval")
public class PatientController {

    @Autowired
    private PatientServiceImpl patientService;


    @Operation(
            summary = "Create user patient ",
            description = "Return success json after successfull creation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient registered successfully",
                    content = @Content(schema = @Schema(implementation = PatientResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request (Validation failed)",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/addDetail")
//    @AuditAction
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto newPatient = patientService.addPatientDetails(patientRequestDto);
        return ResponseEntity.ok().body(newPatient);
    }

    @Operation(
            summary = "List all registered patients",
            description = "Fetches the complete list of patients stored in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients fetched successfully",
                    content = @Content(schema = @Schema(implementation = PatientResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/listAllPatients")
    public ResponseEntity<List<PatientResponseDto>> listAllPatients(){
        List<PatientResponseDto> allPatients = patientService.listPatientDetails();
        System.out.println("Patient list ----> " + allPatients);
        AuditAspect.logEvent("Patient list fetched");
        return ResponseEntity.ok().body(allPatients);

    }

    @Operation(
            summary = "Get patient by ID",
            description = "Returns patient details based on the patient ID provided in the path variable."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient fetched successfully",
                    content = @Content(schema = @Schema(implementation = PatientResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameters({
            @Parameter(
                    name = "id",
                    description = "Unique patient ID used to retrieve patient details",
                    required = true,
                    example = "PAT-10234"
            )
    })
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
