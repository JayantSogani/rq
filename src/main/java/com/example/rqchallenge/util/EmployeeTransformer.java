package com.example.rqchallenge.util;

import com.example.rqchallenge.model.EmployeeWrapper;
import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class EmployeeTransformer {

    final static ObjectMapper mapper = new ObjectMapper();

    public static EmployeeWrapper transform(Map<String, Object> map) {
        return mapper.convertValue(map, EmployeeWrapper.class);
    }

    public static Employee transform(EmployeeWrapper data) {
        final Employee employee = new Employee();
        employee.setEmployeeName(data.getName());
        employee.setEmployeeSalary(data.getSalary());
        employee.setEmployeeAge(data.getAge());
        employee.setId(data.getId());
        return employee;
    }
}
