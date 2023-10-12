package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.*;
import com.example.rqchallenge.service.EmployeeService;
import com.example.rqchallenge.util.ExternalApiHandler;
import com.example.rqchallenge.util.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExternalApiHandler externalApiHandler;

    @SpyBean
    private EmployeeService employeeService;

    GetEmployeeResponse response;

    @BeforeEach
    public void setUp() {
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
    public void testGetAllEmployees() throws Exception {

        when(externalApiHandler.get(any(String.class), any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/")
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(11)));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        String requestJson = "{\"name\":\"test\",\"salary\":123,\"age\":23}";
        // Mock the service response
        EmployeeWrapper cer = new EmployeeWrapper();
        cer.setName("test");
        cer.setSalary(123);
        cer.setAge(23);

        CreateOrSingleGetEmployeeResponse response = new CreateOrSingleGetEmployeeResponse();
        response.setStatus(ResponseStatus.SUCCESS.name());
        response.setData(cer);


        when(externalApiHandler.post(any(String.class), any(), eq(CreateOrSingleGetEmployeeResponse.class)))
                    .thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employee_name", is("test")));
    }

    @Test
    public void testGetEmployeeId() throws Exception {
        // Mock the service response
        EmployeeWrapper cer = new EmployeeWrapper();
        cer.setName("test");
        cer.setSalary(123);
        cer.setAge(23);
        cer.setId(1);

        CreateOrSingleGetEmployeeResponse response = new CreateOrSingleGetEmployeeResponse();
        response.setStatus(ResponseStatus.SUCCESS.name());
        response.setData(cer);


        when(externalApiHandler.get(any(String.class), eq(CreateOrSingleGetEmployeeResponse.class)))
                .thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee_name", is("test")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.employee_salary", is(123)));
    }

    @Test
    public void testDeleteEmployeeId() throws Exception {
        // Mock the service response
        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
        response.setStatus(ResponseStatus.SUCCESS.name());
        response.setMessage("deleted the id with 1");


        when(externalApiHandler.delete(any(String.class), eq(DeleteEmployeeResponse.class)))
                .thenReturn(response);

        final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assert.isTrue(mvcResult.getResponse().getContentAsString().toUpperCase().equals("SUCCESS"));
    }

    @Test
    public void testGetgetEmployeesByNameSearch() throws Exception {

        when(externalApiHandler.get(any(String.class), any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/search/Jayant Sogani")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employee_name", is("Jayant Sogani")));
    }

    @Test
    public void testGetHighestSalary() throws Exception {

        when(externalApiHandler.get(any(String.class), any())).thenReturn(response);

        final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/highestSalary")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assert.isTrue(mvcResult.getResponse().getContentAsString().equals("2000"));

    }

    @Test
    public void testTopTenHighestSalary() throws Exception {

        when(externalApiHandler.get(any(String.class), any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/topTenHighestEarningEmployeeNames")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0]", is("Jayant Sogani10")))
                .andExpect(jsonPath("$[9]", is("Jayant Sogani1")));

    }

    @Test
    public void testGetAllEmployeesFailed() throws Exception {

        when(externalApiHandler.get(any(String.class), any())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetEmployeeId_Failed() throws Exception {
        // Mock the service response
        when(externalApiHandler.get(any(String.class), eq(CreateOrSingleGetEmployeeResponse.class)))
                .thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEmployeeId_Failed() throws Exception {
        when(externalApiHandler.delete(any(String.class), eq(DeleteEmployeeResponse.class)))
                .thenReturn(null);

        final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        Assert.isTrue(mvcResult.getResponse().getContentAsString().toUpperCase().equals("FAILED"));
    }
}
