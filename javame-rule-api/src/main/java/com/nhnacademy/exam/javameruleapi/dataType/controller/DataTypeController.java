package com.nhnacademy.exam.javameruleapi.dataType.controller;

import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeRegisterRequest;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeResponse;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeUpdateRequest;
import com.nhnacademy.exam.javameruleapi.dataType.service.DataTypeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data-types")
@RequiredArgsConstructor
public class DataTypeController {

    @Autowired
    private DataTypeService dataTypeService;

    @PostMapping
    ResponseEntity<DataTypeResponse> registerDataType(@Validated @RequestBody DataTypeRegisterRequest dataTypeRegisterRequest){
       DataTypeResponse dataTypeResponse =  dataTypeService.register(dataTypeRegisterRequest);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(dataTypeResponse);
    }

    @GetMapping("/{type-no}")
    ResponseEntity<DataTypeResponse> getDataType(@PathVariable("{type-no}") long dataTypeNo){
        DataTypeResponse dataTypeResponse = dataTypeService.getDataType(dataTypeNo);
        return ResponseEntity
                .ok(dataTypeResponse);
    }

    @PutMapping("/{type-no}")
    ResponseEntity<DataTypeResponse> updateDataType( @PathVariable long dataTypeNo, @Validated @RequestBody DataTypeUpdateRequest dataTypeUpdateRequest){
        DataTypeResponse dataTypeResponse = dataTypeService.update(dataTypeNo, dataTypeUpdateRequest);
        return ResponseEntity
                .ok(dataTypeResponse);
    }

    @DeleteMapping("/{type-no}")
    ResponseEntity<Void> deleteDataType(@PathVariable("type-no") long dataTypeNo) {
        dataTypeService.delete(dataTypeNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
