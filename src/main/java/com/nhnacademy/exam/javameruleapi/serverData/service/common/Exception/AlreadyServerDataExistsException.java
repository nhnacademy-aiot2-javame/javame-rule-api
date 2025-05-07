package com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception;

public class AlreadyServerDataExistsException extends RuntimeException {
    public AlreadyServerDataExistsException(String message) {
        super(message);
    }
}
