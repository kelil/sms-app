package com.khalil.sms_app.repositories;

import com.khalil.sms_app.models.Division;
import com.khalil.sms_app.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository  extends JpaRepository<Employee,Integer> {
    List<Employee> findAllEmoloyeeByDivision(Division division);
}
