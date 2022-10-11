package com.khalil.sms_app.services;

import com.khalil.sms_app.models.Division;
import com.khalil.sms_app.models.Employee;
import com.khalil.sms_app.payload.response.MessageResponse;
import com.khalil.sms_app.repositories.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployee() {

        return employeeRepository.findAll();
    }

    public ResponseEntity<?> addEmployee(Employee employee) {

        employeeRepository.save(employee);
        employeeRepository.saveAndFlush(null);
        return ResponseEntity.ok().body(new MessageResponse("Employee added Successfully!"));
    }

    public ResponseEntity<?> updateEmployee(Employee employee) {
        employeeRepository.saveAndFlush(employee);
        return ResponseEntity.ok().body(new MessageResponse("Employee Updated Successfully!"));
    }

    public List<Employee> getAllEmployeeByDivision(Division division) {
        return employeeRepository.findAllEmoloyeeByDivision(division);
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }
}
