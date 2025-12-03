package com.vignesh.appointment_service.mapper;

import com.vignesh.appointment_service.dto.AppointmentRequestDto;
import com.vignesh.appointment_service.dto.AppointmentResponseDto;
import com.vignesh.appointment_service.entity.Appointment;
import com.vignesh.appointment_service.entity.AppointmentStatus;
import com.vignesh.healthcare.dto.EmailDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookingMapper {

    public AppointmentResponseDto convertToDTO(AppointmentRequestDto appointmentRequestDto){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setPatientName(appointmentRequestDto.getPatientId());
        appointmentResponseDto.setMessage(appointmentRequestDto.getMessage());
        appointmentResponseDto.setBookingStatus(String.valueOf(AppointmentStatus.FAILED));
        return appointmentResponseDto;
    }

    public AppointmentResponseDto onboardResponseDTO(Appointment appointment){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setPatientName(appointment.getPatientName());
        appointmentResponseDto.setDoctorName(appointment.getDoctorId());
        appointmentResponseDto.setBookingStatus(String.valueOf(appointment.getAppointmentStatus()));
        System.out.println("Booking Date / time : " + appointment.getAppointmentDate() + " "+appointment.getAppointmentTime());
        appointmentResponseDto.setAppointmentDate(appointment.getAppointmentDate().toString());
        appointmentResponseDto.setAppointmentTime(String.valueOf(appointment.getAppointmentTime()));
        appointmentResponseDto.setTo(appointment.getPatientEmailId());
        appointmentResponseDto.setMessage("Appointment scheduled on " + appointment.getAppointmentDate() + "at " + appointment.getAppointmentTime());

        return appointmentResponseDto;
    }

    public EmailDto toEmailDto(AppointmentResponseDto appointment) {
        EmailDto email = new EmailDto();
        email.setTo(appointment.getTo());
        email.setSubject("Appointment Confirmation");
        email.setTemplateName("appointment-confirmation");

        Map<String, String> params = new HashMap<>();
        params.put("patientName", appointment.getPatientName());
        params.put("doctorName", appointment.getDoctorName());
        params.put("appointmentDate", appointment.getAppointmentDate());
        params.put("appointmentTime", appointment.getAppointmentTime());
        params.put("message", appointment.getMessage());

        email.setTemplateParams(params);

        return email;
    }

}
