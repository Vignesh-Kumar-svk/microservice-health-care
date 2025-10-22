package com.vignesh.notification_service.listener;

import com.vignesh.notification_service.dto.EmailDto;
import com.vignesh.notification_service.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentEventListener.class);

    private final EmailService emailService;

    public AppointmentEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "notificationTopic", groupId = "notification-service-group")
    public void handleAppointmentBooking(@Payload EmailDto event,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                         @Header(KafkaHeaders.OFFSET) long offset){
        logger.info("Message Received from topic; {}, partition :{}, offset :{}", topic, partition, offset);
        try{
            emailService.sendAppointmentConfirmation(event);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
