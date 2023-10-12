package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class GetEmployeeResponse {
    private String status;
    @JsonProperty("data")
    private List<Employee> data;
    private String message;

    public GetEmployeeResponse() {

    }

    public GetEmployeeResponse(String status, List<Employee> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetEmployeeResponse that = (GetEmployeeResponse) o;
        return Objects.equals(status, that.status) && Objects.equals(data, that.data) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data, message);
    }
}
