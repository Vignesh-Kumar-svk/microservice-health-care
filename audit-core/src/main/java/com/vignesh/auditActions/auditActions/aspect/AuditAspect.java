package com.vignesh.auditActions.auditActions.aspect;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuditAspect {

    @Before("@annotation(org.springframework.web.bind.annotation.RestController")
    public JSONObject restControllerAuditLogs(){

        JSONObject response = new JSONObject();
        MDC.get("auditId");


        return response;
    }
}
