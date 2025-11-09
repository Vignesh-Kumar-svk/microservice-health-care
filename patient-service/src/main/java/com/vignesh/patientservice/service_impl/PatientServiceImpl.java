package com.vignesh.patientservice.service_impl;

import com.vignesh.patientservice.dto.PatientRequestDto;
import com.vignesh.patientservice.dto.PatientResponseDto;
import com.vignesh.patientservice.mapper.PatientMapper;
import com.vignesh.patientservice.repository.PatientRepository;
import com.vignesh.patientservice.entity.Patient;
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

    public PatientResponseDto getPatientById(String Id){
        Patient patient = patientRepository.getReferenceById(Id);
        return patientResponseMapper.convertToDtoForResponse(patient);
    }


}
