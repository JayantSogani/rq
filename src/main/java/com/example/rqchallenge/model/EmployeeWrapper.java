package com.example.rqchallenge.model;

import java.util.Objects;

public class EmployeeWrapper {

    private int id;
    private String name;
    private int salary;
    private int age;

    public EmployeeWrapper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeWrapper wrapper = (EmployeeWrapper) o;
        return id == wrapper.id && salary == wrapper.salary && age == wrapper.age && Objects.equals(name, wrapper.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, age);
    }
}
