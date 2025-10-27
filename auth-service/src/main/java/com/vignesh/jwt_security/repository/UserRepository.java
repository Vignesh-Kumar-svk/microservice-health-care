package com.vignesh.jwt_security.repository;

import com.vignesh.jwt_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByuserName(String userName);
}
