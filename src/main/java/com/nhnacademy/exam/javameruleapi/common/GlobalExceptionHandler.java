package com.nhnacademy.exam.javameruleapi.common;

import com.nhnacademy.exam.javameruleapi.server.common.exception.AlreadyServerExistException;
import com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception.AlreadyServerDataExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyServerExistException.class)
    public ResponseEntity<Map<String,String>> alreadyServerExistException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error","이미 존재하는 서버입니다."));
    }

    @ExceptionHandler(AlreadyServerDataExistsException.class)
    public ResponseEntity<Map<String,String>> alreadyServerDataExistsException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error","이미 존재하는 서버 데이터입니다."));
    }
}
