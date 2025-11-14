package com.vignesh.appointment_service.client;

import com.vignesh.healthcare.dto.PatientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*This annotation tells Spring to create a Feign client
 that routes requests to the Patient-Service microservice.*/
//@FeignClient(value = "patient-service", url="http://patient-service:8081")
@FeignClient(value = "patient-service/patients")
public interface PatientClient {

    @GetMapping("/patient-id/{id}")
    public ResponseEntity<PatientResponseDto> getPatientByPatientId(@PathVariable("id") String patientId);

}
