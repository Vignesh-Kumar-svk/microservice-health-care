package com.svk.appointment_service.mapper;

import com.svk.appointment_service.dto.AppointmentRequestDto;
import com.svk.appointment_service.dto.AppointmentResponseDto;
import com.svk.appointment_service.entity.Appointment;
import com.svk.appointment_service.entity.AppointmentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class BookingMapper {

    public AppointmentResponseDto convertToDTO(AppointmentRequestDto appointmentRequestDto){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setPatientName(appointmentRequestDto.getPatientId());
        appointmentResponseDto.setMessage("Slot already booked or being booked");
        appointmentResponseDto.setBookingStatus(String.valueOf(AppointmentStatus.FAILED));
        return appointmentResponseDto;
    }

    public AppointmentResponseDto onboardResponseDTO(Appointment appointment){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setPatientName(appointment.getPatientId());
        appointmentResponseDto.setDoctorName(appointment.getDoctorId());
        appointmentResponseDto.setBookingStatus(String.valueOf(appointment.getAppointmentStatus()));
        System.out.println("Booking Date / time : " + appointment.getAppointmentDate() + " "+appointment.getAppointmentTime());
        appointmentResponseDto.setAppointmentDate(appointment.getAppointmentDate().toString());
        appointmentResponseDto.setAppointmentTime(String.valueOf(appointment.getAppointmentTime()));
        appointmentResponseDto.setMessage("Appointment scheduled on " + appointment.getAppointmentDate() + "at " + appointment.getAppointmentTime());

        return appointmentResponseDto;
    }
}
