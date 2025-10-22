package com.vignesh.notification_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDto {
    private String to;
    private String subject;
    private Map<String, String> templateParams; // key=value for dynamic content
    private String body; // optional, can be pre-rendered
    private String templateName; // optional, if using templates
}
