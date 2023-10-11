package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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


}
