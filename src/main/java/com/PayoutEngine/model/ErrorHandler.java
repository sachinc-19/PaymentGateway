package com.PayoutEngine.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
@RequestScope
public class ErrorHandler {
    private List<FieldError> errors = new ArrayList<>();

    public void addError(String field, String message) {
        errors.add(new FieldError(field, message));
    }
}
