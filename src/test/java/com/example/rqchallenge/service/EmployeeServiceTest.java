package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.RecordCreationException;
import com.example.rqchallenge.exception.RecordNotFoundException;
import com.example.rqchallenge.model.*;
import com.example.rqchallenge.util.Constants;
import com.example.rqchallenge.util.EmployeeTransformer;
import com.example.rqchallenge.util.ExternalApiHandler;
import com.example.rqchallenge.util.ResponseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private ExternalApiHandler externalApiHandler;

    GetEmployeeResponse response;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        response = new GetEmployeeResponse();
        response.setStatus("success");
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,  1000, "Jayant Sogani"));
        employees.add(new Employee(2,  1100, "Jayant Sogani1"));
        employees.add(new Employee(3,  1200, "Jayant Sogani2"));
        employees.add(new Employee(4,  1300, "Jayant Sogani3"));
        employees.add(new Employee(5,  1400, "Jayant Sogani4"));
        employees.add(new Employee(6,  1500, "Jayant Sogani5"));
        employees.add(new Employee(7,  1600, "Jayant Sogani6"));
        employees.add(new Employee(8,  1700, "Jayant Sogani7"));
        employees.add(new Employee(9,  1800, "Jayant Sogani8"));
        employees.add(new Employee(10,  1900, "Jayant Sogani9"));
        employees.add(new Employee(11,  2000, "Jayant Sogani10"));
        response.setData(employees);
    }

    @Test
    public void testGetAllEmployee() {
        when(externalApiHandler.get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class))
                .thenReturn(response);

        List<Employee> employees = employeeService.getAllEmployee();

        assert employees.size() == 11;
        assert employees.get(0).getEmployeeName().equals("Jayant Sogani");
    }

    @Test
    public void testCreateEmployee() {
        final Map<String, Object> input = new HashMap<>();
        input.put("name", "test");
        input.put("salary", 123);
        input.put("age", 23);

        final CreateEmployeeRequest request = EmployeeTransformer.transform(input);

        final CreateOrSingleGetEmployeeResponse response = new CreateOrSingleGetEmployeeResponse();
        response.setStatus("success");
        response.setData(request);

        when(externalApiHandler.post(eq(Constants.CREATE_EMPLOYEE_URL), any(CreateEmployeeRequest.class), eq(CreateOrSingleGetEmployeeResponse.class)))
                .thenReturn(response);

        Employee result = employeeService.createEmployee(input);

        verify(externalApiHandler, times(1))
                .post(any(String.class), any(CreateEmployeeRequest.class), any());

        assertNotNull(result);
        Assertions.assertEquals(result.getEmployeeName(), "test");

    }

    @Test
    public void testDeleteEmpById() {
        String id = "1";
        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
        response.setStatus(ResponseStatus.SUCCESS.name());
        // Set response fields as needed
        when(externalApiHandler.delete(eq(String.format(Constants.DELETE_EMPLOYEE, id)), eq(DeleteEmployeeResponse.class)))
                .thenReturn(response);

        String result = employeeService.deleteEmpById(id);

        verify(externalApiHandler, times(1)).delete(String.format(Constants.DELETE_EMPLOYEE, id), DeleteEmployeeResponse.class);

        assertNotNull(result);
    }

    @Test
    public void testGetEmployee() {
        String id = "1";
        final Map<String, Object> input = new HashMap<>();
        input.put("name", "test");
        input.put("salary", 123);
        input.put("age", 23);
        input.put("id", 1);

        CreateOrSingleGetEmployeeResponse response = new CreateOrSingleGetEmployeeResponse();
        response.setStatus(ResponseStatus.SUCCESS.name());
        response.setData(EmployeeTransformer.transform(input));

        when(externalApiHandler.get(eq(String.format(Constants.EMPLOYEE_GET_URL, id)), eq(CreateOrSingleGetEmployeeResponse.class)))
                .thenReturn(response);

        Employee result = employeeService.getEmployee(id);

        verify(externalApiHandler, times(1)).get(String.format(Constants.EMPLOYEE_GET_URL, id), CreateOrSingleGetEmployeeResponse.class);

        assertNotNull(result);
    }

    @Test
    public void testGetEmployeeByName() {
        String searchString = "Jayant Sogani";

        when(externalApiHandler.get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class)).thenReturn(response);

        List<Employee> result = employeeService.getEmployeeByName(searchString);

        verify(externalApiHandler, times(1)).get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class);

        assertNotNull(result);
        assertTrue(result.get(0).getEmployeeName().equals(searchString));
    }

    @Test
    public void testGetHighestSalary() {

        when(externalApiHandler.get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class)).thenReturn(response);

        Integer result = employeeService.getHighestSalary();

        verify(externalApiHandler, times(1)).get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class);

        assertNotNull(result);
        assertEquals(result, 2000);
    }

    @Test
    public void testGetTopTenHighestEmployeeNames() {

        when(externalApiHandler.get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class)).thenReturn(response);

        List<String> result = employeeService.getTopTenHighestEmployeeNames();

        verify(externalApiHandler, times(1)).get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class);

        assertNotNull(result);
        assertTrue(!result.contains("Jayant Sogani"));
        assertEquals(result.size(), 10);

    }

    @Test
    public void testGetAllEmployeeFailed() {
        when(externalApiHandler.get(Constants.EMPLOYEE_URL, GetEmployeeResponse.class))
                .thenReturn(null);

        List<Employee> employees = employeeService.getAllEmployee();

        assert employees.size() == 0;
    }

    @Test()
    public void testCreateEmployeeFailed() throws RecordNotFoundException {
        final Map<String, Object> input = new HashMap<>();
        input.put("name", "test");
        input.put("salary", 123);
        input.put("age", 23);

        final CreateEmployeeRequest request = EmployeeTransformer.transform(input);

        when(externalApiHandler.post(eq(Constants.CREATE_EMPLOYEE_URL), any(CreateEmployeeRequest.class), eq(CreateOrSingleGetEmployeeResponse.class)))
                .thenReturn(null);

        RecordCreationException exception = assertThrows(RecordCreationException.class, () -> {
            employeeService.createEmployee(input);
        });

        assertEquals(exception.getMessage(), "could not able to create emp test");
    }

    @Test
    public void testGetEmployeeFailed() {
        String id = "1";
        final Map<String, Object> input = new HashMap<>();
        input.put("name", "test");
        input.put("salary", 123);
        input.put("age", 23);
        input.put("id", 2);

        CreateOrSingleGetEmployeeResponse response = new CreateOrSingleGetEmployeeResponse();
        response.setStatus(ResponseStatus.SUCCESS.name());
        response.setData(EmployeeTransformer.transform(input));

        when(externalApiHandler.get(eq(String.format(Constants.EMPLOYEE_GET_URL, id)), eq(CreateOrSingleGetEmployeeResponse.class)))
                .thenReturn(null);

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            employeeService.getEmployee(id);
        });

        assertEquals(exception.getMessage(), "Employee not found with id: 1");

    }

    @Test
    public void testDeleteEmpByIdFiled() {
        String id = "1";
        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
        response.setStatus(ResponseStatus.SUCCESS.name());
        // Set response fields as needed
        when(externalApiHandler.delete(eq(String.format(Constants.DELETE_EMPLOYEE, id)), eq(DeleteEmployeeResponse.class)))
                .thenReturn(null);

        String result = employeeService.deleteEmpById(id);

        verify(externalApiHandler, times(1)).delete(String.format(Constants.DELETE_EMPLOYEE, id), DeleteEmployeeResponse.class);

        assertNotNull(result);
        assertEquals(result, ResponseStatus.FAILED.name());
    }


}

