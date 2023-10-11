package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.RecordNotFoundException;
import com.example.rqchallenge.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IEmployeeService {

    public List<Employee> getAllEmployee();

    public Employee createEmployee(Map<String, Object> employee);

    public String deleteEmpById(String id);

    public Employee getEmployee(String id) throws RecordNotFoundException;

    public List<Employee> getEmployeeByName(String searchString);

    public Integer getHighestSalary();

    public List<String> getTopTenHighestEmployeeNames();
}
