package com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception;

public class ServerDataNotExistsException extends RuntimeException {
    public ServerDataNotExistsException(String message) {
        super(message);
    }
}