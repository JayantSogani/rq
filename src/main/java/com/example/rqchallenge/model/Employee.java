package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Employee {

    private int id;
    @JsonProperty("employee_salary")
    private int employeeSalary;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("employee_age")
    private int employeeAge;

    @JsonProperty("profile_image")
    private String profileImage;

    public Employee() {
    }

    public Employee(int id, int employeeSalary, String employeeName) {
        this.id = id;
        this.employeeSalary = employeeSalary;
        this.employeeName = employeeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(int employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && employeeSalary == employee.employeeSalary && employeeAge == employee.employeeAge && Objects.equals(employeeName, employee.employeeName) && Objects.equals(profileImage, employee.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeSalary, employeeName, employeeAge, profileImage);
    }
}
