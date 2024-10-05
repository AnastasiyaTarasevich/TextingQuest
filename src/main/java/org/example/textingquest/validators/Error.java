package org.example.textingquest.validators;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {

    String code;
    String message;
}
