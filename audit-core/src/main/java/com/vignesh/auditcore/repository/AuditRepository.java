package com.vignesh.auditcore.repository;


import com.vignesh.auditcore.entity.AuditDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditDetails, Long> {

}
