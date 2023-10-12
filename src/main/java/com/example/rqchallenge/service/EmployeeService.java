package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.RecordCreationException;
import com.example.rqchallenge.exception.RecordNotFoundException;
import com.example.rqchallenge.model.*;
import com.example.rqchallenge.util.EmployeeTransformer;
import com.example.rqchallenge.util.ExternalApiHandler;
import com.example.rqchallenge.util.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.rqchallenge.util.Constants.*;

@Component
public class EmployeeService implements IEmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private ExternalApiHandler apiHandler;
    @Override
    public List<Employee> getAllEmployee() {
        final GetEmployeeResponse response = apiHandler.get(EMPLOYEE_URL, GetEmployeeResponse.class);
        if (null == response) {
            logger.debug("null response for the getAllEmployee");
            return Collections.emptyList();
        }

        if (ResponseStatus.valueOf(response.getStatus().toUpperCase()) != ResponseStatus.SUCCESS) {
            logger.debug("status is {}, for getAllEmployee", response.getStatus());
            return Collections.emptyList();
        }

        return response.getData();
    }

    @Override
    public Employee createEmployee(Map<String, Object> employeeMap) {
        final EmployeeWrapper employeeRequest = EmployeeTransformer.transform(employeeMap);
        final CreateOrSingleGetEmployeeResponse response = apiHandler.post(CREATE_EMPLOYEE_URL, employeeRequest, CreateOrSingleGetEmployeeResponse.class);
        if (null == response) {
            logger.debug("create employee is failed for the {}", employeeRequest.getName());
            throw new RecordCreationException("could not able to create emp " + employeeRequest.getName());
        }
        return EmployeeTransformer.transform(response.getData());
    }

    @Override
    public String deleteEmpById(String id) {
        final DeleteEmployeeResponse response = apiHandler.delete(String.format(DELETE_EMPLOYEE, id),
                DeleteEmployeeResponse.class);
        if (null == response) {
            logger.debug("could not able to delete an employee for id {}", id);
            return ResponseStatus.FAILED.name();
        }
        return response.getStatus();
    }

    @Override
    public Employee getEmployee(String id) throws RecordNotFoundException {
        final CreateOrSingleGetEmployeeResponse response = apiHandler.get(String.format(EMPLOYEE_GET_URL, id),
                CreateOrSingleGetEmployeeResponse.class);
        if (null == response || null == response.getData()) {
            throw new RecordNotFoundException("Employee not found with id: " + id);
        }
        return EmployeeTransformer.transform(response.getData());
    }

    public List<Employee> getEmployees() {
        final List<Employee> employees = getAllEmployee();
        if (null == employees || CollectionUtils.isEmpty(employees)) {
            logger.debug("no employees are present at the moment");
            return Collections.emptyList();
        }
        return employees;
    }

    @Override
    public List<Employee> getEmployeeByName(String searchString) {
        return getEmployees().stream()
                .filter(e -> searchString.equals(e.getEmployeeName()))
                .collect(Collectors.toList());

    }

    @Override
    public Integer getHighestSalary() {
        return getEmployees().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Employee::getEmployeeSalary)))
                .map(e -> e.getEmployeeSalary())
                .get();
    }

    @Override
    public List<String> getTopTenHighestEmployeeNames() {
        return getEmployees().stream()
                .sorted(Comparator.comparing(Employee::getEmployeeSalary).reversed())
                .distinct()
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
    }
}
