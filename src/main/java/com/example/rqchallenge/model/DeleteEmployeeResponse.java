package com.example.rqchallenge.model;

import java.util.Objects;

public class DeleteEmployeeResponse {

    private String status;
    private String message;
    public DeleteEmployeeResponse() {
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        DeleteEmployeeResponse that = (DeleteEmployeeResponse) o;
        return Objects.equals(status, that.status) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }
}
