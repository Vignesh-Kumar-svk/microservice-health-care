package com.vignesh.auditcore.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

public class AuditFilterInterceptor implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Inside Audit core filter");

        HttpServletRequest request= (HttpServletRequest) servletRequest;

        String auditId = request.getHeader("X-Audit-Id");
        if(auditId==null){
            auditId = "vignesh"+ UUID.randomUUID().toString();
        }

        MDC.put("auditId",auditId);
        try{
            filterChain.doFilter(request, servletResponse);
        }
        finally {
            MDC.clear();
        }
    }
}
