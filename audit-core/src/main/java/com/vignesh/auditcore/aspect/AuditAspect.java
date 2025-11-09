package com.vignesh.auditcore.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    public static void logEvent(String msg) {
        logger.info("AUDIT EVENT: {}", msg);
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void beforeRestCall(JoinPoint jp) {
        logger.info("AUDIT_START | controller={} | method={} | auditId={}",
                jp.getTarget().getClass().getSimpleName(),
                jp.getSignature().getName(),
                org.slf4j.MDC.get("auditId"));
    }

    @AfterReturning("within(@org.springframework.web.bind.annotation.RestController *)")
    public void afterRestCall(JoinPoint jp) {
        logger.info("AUDIT_SUCCESS | controller={} | method={} | auditId={}",
                jp.getTarget().getClass().getSimpleName(),
                jp.getSignature().getName(),
                org.slf4j.MDC.get("auditId"));
    }

    @AfterThrowing(value = "within(@org.springframework.web.bind.annotation.RestController *)", throwing = "ex")
    public void onError(JoinPoint jp, Exception ex) {
        logger.error("AUDIT_ERROR | controller={} | method={} | auditId={} | error={}",
                jp.getTarget().getClass().getSimpleName(),
                jp.getSignature().getName(),
                org.slf4j.MDC.get("auditId"),
                ex.getMessage());
    }

}
