package com.svk.appointment_service.service_impl;

import com.svk.appointment_service.dto.AppointmentRequestDto;
import com.svk.appointment_service.dto.AppointmentResponseDto;
import com.svk.appointment_service.entity.Appointment;
import com.svk.appointment_service.entity.AppointmentStatus;
import com.svk.appointment_service.repository.AppointmentRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
public class AppointmentServiceImpl {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentResponseDto appointmentResponseDto;

    private final RedissonClient redissonClient;


    private final RestTemplate restTemplate;

    public AppointmentServiceImpl(RedissonClient redissonClient, RestTemplate restTemplate) {
        this.redissonClient = redissonClient;
        this.restTemplate = restTemplate;
    }

    public AppointmentResponseDto bookingAppointment(AppointmentRequestDto appointmentRequestDto){

        String lockKey = "appointment-slot-lock:"+ appointmentRequestDto.getAppointmentDate() + ":" + appointmentRequestDto.getAppointmentTime();
        RLock lock = redissonClient.getLock(lockKey);



        try {
            boolean isLocked =  lock.tryLock(5, 10, TimeUnit.SECONDS);
            //Slot is being booked by someone else. Please try again!";
            if(!isLocked){

//                // Call Patient Service
//                String patientServiceUrl = "http://PATIENT-SERVICE/patients/" + appointment.getPatientId();
//                Patient patient = restTemplate.getForObject(patientServiceUrl, PatientDTO.class);

                appointmentResponseDto.setPatientName(appointmentRequestDto.getPatientId());
                appointmentResponseDto.setMessage("Slot is being booked by someone else. Please try again!");
                appointmentResponseDto.setBookingStatus(String.valueOf(AppointmentStatus.FAILED));
                return appointmentResponseDto;
            }

            LocalDate date = LocalDate.parse(appointmentRequestDto.getAppointmentDate());
            LocalTime time = LocalTime.parse((appointmentRequestDto.getAppointmentTime()));

            if(appointmentRepository.existsByAppointmentDateAndAppointmentTime(date, time)){

                //"Slot already booked!";
                appointmentResponseDto.setPatientName(appointmentRequestDto.getPatientId());
                appointmentResponseDto.setMessage("Slot already booked!");
                appointmentResponseDto.setBookingStatus(String.valueOf(AppointmentStatus.FAILED));
                return appointmentResponseDto;
            }

            Appointment appointment = new Appointment();
            appointment.setPatientId(appointmentRequestDto.getPatientId());
            appointment.setDoctorId(appointmentRequestDto.getDoctorId());
            appointment.setAppointmentDate(LocalDate.parse(appointmentRequestDto.getAppointmentDate()));
            appointment.setAppointmentTime(LocalTime.parse(appointmentRequestDto.getAppointmentTime()));
            appointment.setAppointmentStatus(String.valueOf(AppointmentStatus.BOOKED));

            return  appointmentResponseDto;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
