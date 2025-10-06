package com.svk.patient_service.service_impl;

import com.svk.patient_service.dto.PatientRequestDto;
import com.svk.patient_service.dto.PatientResponseDto;
import com.svk.patient_service.mapper.PatientMapper;
import com.svk.patient_service.repository.PatientRepository;
import com.svk.patient_service.entity.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientMapper patientResponseMapper;

    public PatientResponseDto addPatientDetails(PatientRequestDto patientRequestDto){
       Patient newPatient =  patientRepository.save(patientResponseMapper.toDTO(patientRequestDto));
       return patientResponseMapper.convertToDtoForResponse(newPatient);
    }

    public List<PatientResponseDto> listPatientDetails(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patientResponseMapper::convertToDtoForResponse).toList();

    }
}
