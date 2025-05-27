package com.nhnacademy.exam.javameruleapi.server.common.exception;

public class AlreadyServerExistException extends RuntimeException {
    public AlreadyServerExistException(String message){
        super(message);
    }
}
