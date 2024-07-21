package com.hepsiemlak.todo.exception;

public class UserNotLoginException extends RuntimeException {
    public UserNotLoginException(String message) {
        super(message);
    }
}
