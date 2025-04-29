package com.nhnacademy.exam.javameruleapi.server.common.Exception;

public class ServerNotExistException extends RuntimeException {
    public ServerNotExistException(String message) {
        super(message);
    }
}
