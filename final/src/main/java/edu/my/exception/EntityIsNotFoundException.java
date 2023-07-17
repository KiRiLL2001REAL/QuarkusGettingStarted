package edu.my.exception;

import jakarta.validation.ValidationException;

public class EntityIsNotFoundException extends ValidationException {
    public EntityIsNotFoundException(String message) {
        super(message);
    }
}
