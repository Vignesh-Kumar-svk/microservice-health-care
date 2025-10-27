package com.vignesh.auditActions.auditActions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "audit_details")
public class AuditDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String auditId;

    @Column(nullable = false)
    private String auditType; // CREATE_ORDER, LOGIN, etc.

    @Column(nullable = false)
    private String userId;
    private String serviceName; // origin service
    private String status; // SUCCESS / FAILED
    private Map<String, Object> comments; // flexible payload
    private Instant timestamp;

}
