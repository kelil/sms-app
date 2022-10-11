package com.khalil.sms_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khalil.sms_app.models.Division;
import com.khalil.sms_app.models.Employee;
import com.khalil.sms_app.payload.response.MessageResponse;
import com.khalil.sms_app.repositories.DivisionRepository;
import com.khalil.sms_app.services.EmployeeService;

@CrossOrigin(origins = "http://10.1.11.145:4200", maxAge = 360, allowCredentials = "true")
@RestController
@RequestMapping(path = "api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    private DivisionRepository divisionRepository;

    public EmployeeController(EmployeeService employeeService, DivisionRepository divisionRepository) {
        this.employeeService = employeeService;
        this.divisionRepository = divisionRepository;
    }

    @GetMapping()
    public List<Employee> getEmployees(){
        return employeeService.findAllEmployee();
    }

    @PostMapping("/employee")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        return  employeeService.addEmployee(employee);
    }

    @GetMapping("/division/{id}")
    public List<Employee> getAllEmployeeByDivision(@PathVariable(value = "id") Integer id){
        Division division = divisionRepository.findById(id).get();
        return employeeService.getAllEmployeeByDivision(division);
    }
    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable(value = "id") Integer id){
        return employeeService.getEmployeeById(id);
    }

    @PatchMapping("/employee/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateEmployee(@PathVariable(value = "id") Integer id, @RequestBody Employee employee){
        Employee employee2 = employeeService.getEmployeeById(id);
        employee2.setGivenName(employee.getGivenName());
        employee2.setDivision(employee.getDivision());
        employee2.setFatherName(employee.getFatherName());
        employee2.setGrandFatherName(employee.getGrandFatherName());
        employee2.setPhoneNumber(employee.getPhoneNumber());
        employee2.setPosition(employee.getPosition());
        employee2.setEmail(employee.getEmail());
        return employeeService.updateEmployee(employee2);
    }

    @DeleteMapping("/employee/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Integer id){
        employeeService.deleteById(id);
        return ResponseEntity.ok().body(new MessageResponse("Deleted SuccessFully"));
    }




}
