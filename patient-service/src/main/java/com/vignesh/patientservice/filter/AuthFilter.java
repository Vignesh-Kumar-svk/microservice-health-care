package com.vignesh.patientservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        String authHeader = httpServletRequest.getHeader("authorization");

        String userId = httpServletRequest.getHeader("X-User-Id");
        String role = httpServletRequest.getHeader("X-User-Role");

        // Basic check — request must come through Gateway
        if (userId == null) {
            httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing authentication headers");
            return;
        }

        // Optional Role Check Example:
        if (httpServletRequest.getRequestURI().contains("/admin") && !"ADMIN".equals(role)) {
            httpRes.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not allowed");
            return;
        }

        filterChain.doFilter(request,response);
    }
}
