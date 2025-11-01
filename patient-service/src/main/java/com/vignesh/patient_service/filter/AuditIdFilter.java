package com.vignesh.patient_service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.logging.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class AuditIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String auditId = request.getHeader("X-Audit-ID"); // incoming from another service, if any
        if (auditId == null) {
            auditId = UUID.randomUUID().toString(); // root request
        }

        // Store in MDC for logging and aspects
        MDC.put("auditId", auditId);

        // Optionally store in SecurityContext (if you use custom context)
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("system", null, List.of())
        );

        // Add it back to response (for clients)
        response.addHeader("X-Audit-ID", auditId);

        filterChain.doFilter(request, response);

        MDC.remove("auditId");
    }
}

