package com.svk.jwt_security.repository;

import com.svk.jwt_security.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findByroleName(String name);
}
