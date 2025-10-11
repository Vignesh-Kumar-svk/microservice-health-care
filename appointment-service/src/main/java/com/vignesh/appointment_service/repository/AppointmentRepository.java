package com.vignesh.appointment_service.repository;

import com.vignesh.appointment_service.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    Boolean existsByAppointmentDateAndAppointmentTime(LocalDate appointmentDate, LocalTime appointmentTime);


}
