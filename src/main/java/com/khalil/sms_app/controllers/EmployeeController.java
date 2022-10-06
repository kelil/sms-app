package com.khalil.sms_app.controllers;

import com.khalil.sms_app.models.Division;
import com.khalil.sms_app.models.Employee;
import com.khalil.sms_app.models.EmployeeDTO;
import com.khalil.sms_app.repositories.DivisionRepository;
import com.khalil.sms_app.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setGivenName(employeeDTO.getGivenName());
        employee.setFatherName(employeeDTO.getFatherName());
        employee.setGrandFatherName(employeeDTO.getGrandFatherName());
        employee.setPosition(employeeDTO.getPosition());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        try {
            employee.setDivision(divisionRepository.findById(employeeDTO.getDivision()).get());

        }catch (Exception e){
            System.out.println(e);
        }

        return  employeeService.addEmployee(employee);
    }

    @GetMapping("/division/{id}")
    public List<Employee> getAllEmployeeByDivision(@PathVariable(value = "id") Integer id){
        Division division = divisionRepository.findById(id).get();
        return employeeService.getAllEmployeeByDivision(division);
    }


}
