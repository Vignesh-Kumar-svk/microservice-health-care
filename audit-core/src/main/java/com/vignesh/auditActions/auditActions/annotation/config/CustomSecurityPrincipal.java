package com.vignesh.auditActions.auditActions.annotation.config;

import java.io.Serializable;

public class CustomSecurityPrincipal implements Serializable {
    private final String username;
    private final String auditId;

    public CustomSecurityPrincipal(String username, String auditId) {
        this.username = username;
        this.auditId = auditId;
    }

    public String getUsername() {
        return username;
    }

    public String getAuditId() {
        return auditId;
    }
}

