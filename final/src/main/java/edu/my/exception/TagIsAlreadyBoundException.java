package edu.my.exception;


import jakarta.validation.ValidationException;

public class TagIsAlreadyBoundException extends ValidationException {
    public TagIsAlreadyBoundException(String message) {
        super(message);
    }
}
