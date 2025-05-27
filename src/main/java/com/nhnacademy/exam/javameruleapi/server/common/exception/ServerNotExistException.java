package com.nhnacademy.exam.javameruleapi.server.common.exception;

public class ServerNotExistException extends RuntimeException {
    public ServerNotExistException(String message) {
        super(message);
    }
}
