package com.nhnacademy.exam.javameruleapi.server.repository.Exception;

public class ServerNotExistException extends RuntimeException {
    public ServerNotExistException(String message) {
        super(message);
    }
}
