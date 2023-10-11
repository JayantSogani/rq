package com.example.rqchallenge.model;

import java.util.Objects;

public class CreateOrSingleGetEmployeeResponse {

    private String status;

    private CreateEmployeeRequest data;

    public CreateOrSingleGetEmployeeResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CreateEmployeeRequest getData() {
        return data;
    }

    public void setData(CreateEmployeeRequest data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrSingleGetEmployeeResponse that = (CreateOrSingleGetEmployeeResponse) o;
        return Objects.equals(status, that.status) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data);
    }
}
