package com.vignesh.auditActions.auditActions.repository;

import com.vignesh.auditActions.auditActions.entity.AuditDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditDetails, Long> {

}
