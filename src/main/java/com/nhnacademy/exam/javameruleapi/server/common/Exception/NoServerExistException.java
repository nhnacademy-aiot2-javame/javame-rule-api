package com.nhnacademy.exam.javameruleapi.server.common.Exception;

public class NoServerExistException extends RuntimeException {
    public NoServerExistException(String message) {
        super(message);
    }
}
