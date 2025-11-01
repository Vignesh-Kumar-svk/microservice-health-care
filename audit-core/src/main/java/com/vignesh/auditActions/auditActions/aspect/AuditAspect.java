package com.vignesh.auditActions.auditActions.aspect;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vignesh.auditActions.auditActions.annotation.config.CustomSecurityPrincipal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Aspect
@Component
public class AuditAspect {

    Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void allRestControllers() {}


    @Before("@allRestControllers()")
    public void restControllerAuditLogs(JoinPoint joinPoint){

        try {
            // 1️⃣ Generate a random Audit ID if not already present
            String auditId = MDC.get("auditId");
//            if (auditId == null || auditId.isBlank()) {
//                auditId = UUID.randomUUID().toString();
//                MDC.put("auditId", auditId);
//            }

            // 2️⃣ Add to SecurityContext (optional: wrap inside custom Authentication object)
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            if (currentAuth == null) {
                // create a dummy Authentication if not set
                currentAuth = new UsernamePasswordAuthenticationToken("system", null, List.of());
            }

            // Create custom principal with auditId
            CustomSecurityPrincipal principal = new CustomSecurityPrincipal(currentAuth.getName(), auditId);
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    principal,
                    currentAuth.getCredentials(),
                    currentAuth.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            logger.debug("✅ Audit ID [{}] set in SecurityContext for {}", auditId, joinPoint.getSignature());

        } catch (Exception ex) {
            logger.error("Failed to initialize Audit ID", ex);
        }
    }

}
