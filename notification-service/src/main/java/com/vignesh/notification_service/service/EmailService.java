package com.vignesh.notification_service.service;

import com.vignesh.notification_service.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailService(){

    }

    public void sendAppointmentConfirmation(EmailDto event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getTo());
        message.setSubject("Appointment Confirmation");
        message.setText("Hi " + event.getTemplateParams().get("name") + ", " +
                "your appointment is confirmed on " + event.getTemplateParams().get("appointmentDate") +
                "at " + event.getTemplateParams().get("appointmentTime"));
        message.setFrom("vigneshkumaryes25@gmail.com");
        log.info(String.valueOf(message));
        mailSender.send(message);
    }

    public void sendAppointmentCancelled(EmailDto event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getTo());
        message.setSubject("Appointment Confirmation");
        message.setText("Hi " + event.getTemplateParams().get("name") + ", " +
                "your appointment is cancelled for the scheduled date " + event.getTemplateParams().get("appointmentDate") +
                "at " + event.getTemplateParams().get("appointmentTime"));
        mailSender.send(message);
    }
}
