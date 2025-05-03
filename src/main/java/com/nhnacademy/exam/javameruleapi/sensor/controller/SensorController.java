//package com.nhnacademy.exam.javameruleapi.sensor.controller;
//
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
//import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/sensors")
//public class SensorController {
//
//    private final SensorService sensorService;
//
//
//    public SensorController(SensorService sensorService) {
//        this.sensorService = sensorService;
//    }
//
//    @PostMapping
//    ResponseEntity<SensorResponse> registerSensor(@Validated @RequestBody SensorRegisterRequest sensorRegisterRequest){
//        SensorResponse sensorResponse = sensorService.register(sensorRegisterRequest);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(sensorResponse);
//    }
//
//    @GetMapping("/{sensor-id}")
//    //sensorDataList도 같이 보여주도록 수정
//    ResponseEntity<SensorResponse> getSensor(@PathVariable("sensor-id") long sensorNo){
//        SensorResponse sensorResponse = sensorService.getSensor(sensorNo);
//        return ResponseEntity.ok(sensorResponse);
//    }
//
//    @GetMapping
//    //sensorDataList같이 보여줄 수 있게 수정
//    ResponseEntity<List<SensorResponse>> getSensors(@RequestParam String companyDomain){
//        List<SensorResponse> sensorResponses = sensorService.getSensors(companyDomain);
//        return ResponseEntity.ok(sensorResponses);
//    }
//
//
//    @DeleteMapping("/{sensor-id}")
//    ResponseEntity<Void> deleteSensor(@PathVariable("sensor-id") long sensorNo){
//        sensorService.delete(sensorNo);
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .build();
//    }
//
//
//
//}
