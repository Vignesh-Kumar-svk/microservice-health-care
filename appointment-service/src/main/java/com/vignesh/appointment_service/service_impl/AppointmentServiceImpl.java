package com.vignesh.appointment_service.service_impl;

import com.vignesh.appointment_service.dto.AppointmentRequestDto;
import com.vignesh.appointment_service.dto.AppointmentResponseDto;
import com.vignesh.appointment_service.entity.Appointment;
import com.vignesh.appointment_service.entity.AppointmentStatus;
import com.vignesh.appointment_service.event.KafkaPublisher;
import com.vignesh.appointment_service.mapper.BookingMapper;
import com.vignesh.appointment_service.repository.AppointmentRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
public class AppointmentServiceImpl {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    BookingMapper bookingMapper;

    private final RedissonClient redissonClient;

    private final KafkaPublisher kafkaPublisher;


//    private final RestTemplate restTemplate;

    public AppointmentServiceImpl(RedissonClient redissonClient, KafkaTemplate kafkaTemplate, KafkaPublisher kafkaPublisher) {
        this.redissonClient = redissonClient;
        this.kafkaPublisher = kafkaPublisher;
//        this.restTemplate = restTemplate;
    }


    public AppointmentResponseDto bookingAppointment(AppointmentRequestDto appointmentRequestDto){

        String lockKey = "appointment-slot-lock:"+ appointmentRequestDto.getAppointmentDate() + ":" + appointmentRequestDto.getAppointmentTime();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();

            boolean isLocked =  lock.tryLock(5, 10, TimeUnit.SECONDS);
            //Slot is being booked by someone else. Please try again!";
            if(!isLocked){
//                // Call Patient Service
//                String patientServiceUrl = "http://PATIENT-SERVICE/patients/" + appointment.getPatientId();
//                Patient patient = restTemplate.getForObject(patientServiceUrl, PatientDTO.class);
                return bookingMapper.convertToDTO(appointmentRequestDto);

            }

            LocalDate date = LocalDate.parse(appointmentRequestDto.getAppointmentDate());
            LocalTime time = LocalTime.parse((appointmentRequestDto.getAppointmentTime()));

            if(appointmentRepository.existsByAppointmentDateAndAppointmentTime(date, time)){
                //"Slot already booked!";
                return bookingMapper.convertToDTO(appointmentRequestDto);

            }

            Appointment appointment = getAppointment(appointmentRequestDto);
            appointmentRepository.save(appointment);

            AppointmentResponseDto  response = bookingMapper.onboardResponseDTO(appointment);
//            kafkaPublisher.publishAppointment(response);

            kafkaPublisher.publishAppointment(bookingMapper.toEmailDto(response));

            return response;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    private Appointment getAppointment(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(appointmentRequestDto.getPatientId());
        appointment.setDoctorId(appointmentRequestDto.getDoctorId());
        appointment.setAppointmentDate(LocalDate.parse(appointmentRequestDto.getAppointmentDate()));
        appointment.setAppointmentTime(LocalTime.parse(appointmentRequestDto.getAppointmentTime()));
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
        appointment.setPatientEmailId(appointmentRequestDto.getPatientEmailId());
        return appointment;
    }
}
