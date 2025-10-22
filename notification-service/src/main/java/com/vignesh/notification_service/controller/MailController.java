package com.vignesh.notification_service.controller;

import com.vignesh.notification_service.dto.EmailDto;
import com.vignesh.notification_service.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDto emailDto){
        logger.info("HI Noti Contoller -----> sendEmail");
        emailService.sendAppointmentConfirmation(emailDto);
        return "mail sent successfully";
    }
}
