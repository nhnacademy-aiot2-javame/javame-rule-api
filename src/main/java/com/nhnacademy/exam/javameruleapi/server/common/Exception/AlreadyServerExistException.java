package com.nhnacademy.exam.javameruleapi.server.common.Exception;

public class AlreadyServerExistException extends RuntimeException {
    public AlreadyServerExistException(String message){
        super(message);
    }
}
