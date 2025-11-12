package com.vignesh.appointment_service.event;

import com.vignesh.healthcare.dto.EmailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaPublisher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);
    private final KafkaTemplate<String, EmailDto> kafkaTemplate;



    public KafkaPublisher(KafkaTemplate<String, EmailDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAppointment(EmailDto emailDto){
        CompletableFuture<SendResult<String, EmailDto>> future =  kafkaTemplate.send("notificationTopic", emailDto);

        future.whenComplete((result,exception)->{
            //That is exception obj contains any exception
            if(exception!=null){
                logger.error("Failed to send  the message {}", exception.getMessage());
            }
            else{
                logger.info("Message sent with offset {}", result.getRecordMetadata().offset());
            }
        });
    }
}
