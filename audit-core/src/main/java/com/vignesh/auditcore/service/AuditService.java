package com.vignesh.auditcore.service;

import com.vignesh.auditcore.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public void saveAuditDetails(){

    }
}
