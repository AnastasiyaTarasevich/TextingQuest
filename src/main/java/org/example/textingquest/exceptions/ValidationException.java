package org.example.textingquest.exceptions;

import lombok.Getter;
import org.example.textingquest.validators.Error;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;
    public ValidationException(List<Error> errors) {

        this.errors=errors;
    }
}
