package com.example.rqchallenge.controller;

import com.example.rqchallenge.exception.RecordNotFoundException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Component
public class EmployeeController implements IEmployeeController {

    @Autowired
    IEmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        List<Employee> allEmployee = employeeService.getAllEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(allEmployee);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employees = employeeService.getEmployeeByName(searchString);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) throws RecordNotFoundException {
        final Employee employee = employeeService.getEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        Integer maxSalary = employeeService.getHighestSalary();
        return ResponseEntity.status(HttpStatus.OK).body(maxSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> topTenHighestEmployeeNames = employeeService.getTopTenHighestEmployeeNames();
        return ResponseEntity.status(HttpStatus.OK).body(topTenHighestEmployeeNames);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        Employee employee = employeeService.createEmployee(employeeInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        final String response = employeeService.deleteEmpById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
