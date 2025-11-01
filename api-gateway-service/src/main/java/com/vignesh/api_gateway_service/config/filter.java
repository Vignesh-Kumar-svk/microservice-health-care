package com.vignesh.api_gateway_service.config;

import jakarta.servlet.ServletException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Filter;
import jakarta.servlet.http.*;

public class filter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws ServletException, IOException {


    }
}
