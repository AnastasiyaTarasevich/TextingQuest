package org.example.textingquest.validators;

public interface Validator <T>{
    ValidationResult isValid(T object);
}
