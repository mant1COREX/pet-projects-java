package ru.ruslan.spring.SensorsService.util;

import java.util.List;

public class ValidationErrorResponse {

    private List<String> errors;
    private Long timestamp;

    public ValidationErrorResponse(List<String> errors, Long timestamp) {
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
