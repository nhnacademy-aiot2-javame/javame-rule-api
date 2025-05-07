//package com.nhnacademy.exam.javameruleapi.sensorData.controller;
//
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;
//import com.nhnacademy.exam.javameruleapi.sensorData.service.SensorDataService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/sensor-datas")
//@RequiredArgsConstructor
//public class SensorDataController {
//
//    @Autowired
//    private SensorDataService sensorDataService;
//
//    @PostMapping
//    ResponseEntity<SensorDataResponse> registerDataType(@Validated @RequestBody SensorDataRegisterRequest sensorDataRegisterRequest){
//       SensorDataResponse sensorDataResponse =  sensorDataService.register(sensorDataRegisterRequest);
//       return ResponseEntity
//               .status(HttpStatus.CREATED)
//               .body(sensorDataResponse);
//    }
//
//    @GetMapping("/{type-no}")
//    ResponseEntity<SensorDataResponse> getDataType(@PathVariable("type-no") long sensorDataNo){
//        SensorDataResponse sensorDataResponse = sensorDataService.getSensorData(sensorDataNo);
//        return ResponseEntity
//                .ok(sensorDataResponse);
//    }
//
//    @PutMapping("/{type-no}")
//    ResponseEntity<SensorDataResponse> updateDataType(@PathVariable("type-no") long dataTypeNo, @Validated @RequestBody SensorDataUpdateRequest sensorDataUpdateRequest){
//        SensorDataResponse sensorDataResponse = sensorDataService.update(dataTypeNo, sensorDataUpdateRequest);
//        return ResponseEntity
//                .ok(sensorDataResponse);
//    }
//
//    @DeleteMapping("/{type-no}")
//    ResponseEntity<Void> deleteDataType(@PathVariable("type-no") long dataTypeNo) {
//        sensorDataService.delete(dataTypeNo);
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .build();
//    }
//}
