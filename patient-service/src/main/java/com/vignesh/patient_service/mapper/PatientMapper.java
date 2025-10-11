package com.vignesh.patient_service.mapper;

import com.vignesh.patient_service.dto.PatientRequestDto;
import com.vignesh.patient_service.dto.PatientResponseDto;
import com.vignesh.patient_service.entity.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PatientMapper {

    public PatientResponseDto convertToDtoForResponse(Patient patient){
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setName(patient.getName());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setAge(String.valueOf(patient.getAge()));
        return patientResponseDto;
    }

    public Patient toDTO(PatientRequestDto patientRequestDto){
        Patient patient = new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setAge(Integer.parseInt(patientRequestDto.getAge()));
        patient.setContact(patientRequestDto.getContact());
        patient.setGender(patientRequestDto.getGender());
        patient.setMedicalHistory(patientRequestDto.getMedicalHistory());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setRegistered_date(LocalDate.parse(patientRequestDto.getRegistered_date()));
        return patient;
    }
}
