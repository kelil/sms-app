package com.khalil.sms_app.repositories;

import com.khalil.sms_app.models.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepository extends JpaRepository<Division,Integer> {
}
