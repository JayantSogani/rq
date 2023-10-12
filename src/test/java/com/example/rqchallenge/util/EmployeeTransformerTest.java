package com.example.rqchallenge.util;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class EmployeeTransformerTest {

    @Test
    public void testTransformMap() {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", "test_name");
        map.put("salary", 2000);
        map.put("age", 24);
        final EmployeeWrapper transform = EmployeeTransformer.transform(map);
        Assert.isTrue(transform.getName().equals(map.get("name")));
        Assert.isTrue(transform.getSalary() == (int)map.get("salary"));

    }


    @Test
    public void testTransformWrapper() {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", "test_name");
        map.put("salary", 2000);
        map.put("age", 24);
        final EmployeeWrapper wrapper = EmployeeTransformer.transform(map);
        final Employee employee = EmployeeTransformer.transform(wrapper);
        Assert.isTrue(employee.getEmployeeName().equals(map.get("name")));
        Assert.isTrue(employee.getEmployeeSalary() == (int)map.get("salary"));
    }
}
