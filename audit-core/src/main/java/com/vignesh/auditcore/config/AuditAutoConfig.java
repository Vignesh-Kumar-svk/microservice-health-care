package com.vignesh.auditcore.config;

import com.vignesh.auditcore.aspect.AuditAspect;
import com.vignesh.auditcore.filter.AuditFilterInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class AuditAutoConfig {

    @Bean
    public FilterRegistrationBean<AuditFilterInterceptor> auditFilter(){
        FilterRegistrationBean<AuditFilterInterceptor> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuditFilterInterceptor());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public AuditAspect auditAspect(){
        return new AuditAspect();
    }
}
