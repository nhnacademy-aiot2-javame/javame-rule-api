package com.nhnacademy.exam.javameruleapi.server.Exception;

public class ServerNotExistException extends RuntimeException {
    public ServerNotExistException(String message) {
        super(message);
    }
}
