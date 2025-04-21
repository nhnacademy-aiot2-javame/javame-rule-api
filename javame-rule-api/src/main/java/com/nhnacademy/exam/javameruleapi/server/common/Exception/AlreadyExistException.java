package com.nhnacademy.exam.javameruleapi.server.common.Exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message){
        super(message);
    }
}
