package com.khalil.sms_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khalil.sms_app.models.ERole;
import com.khalil.sms_app.models.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

   Optional<Role> findByName(ERole roleUser);
    
}
