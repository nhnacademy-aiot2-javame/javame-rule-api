package com.nhnacademy.exam.javameruleapi.sensor.controller;

import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorThresholdRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorThresholdUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;


    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    ResponseEntity<SensorResponse> registerSensorThreshold(@Validated @RequestBody SensorThresholdRegisterRequest sensorThresholdRegisterRequest){
        SensorResponse sensorResponse = sensorService.register(sensorThresholdRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sensorResponse);
    }

    @GetMapping("/{sensor-id}")
    ResponseEntity<SensorResponse> getSensorThreshold(@PathVariable("sensor-id") long sensorNo){
        SensorResponse sensorResponse = sensorService.getSensorThreshold(sensorNo);
        return ResponseEntity.ok(sensorResponse);
    }

    @PutMapping("/{sensor-id}")
    ResponseEntity<SensorResponse> updateSensorThreshold(@Validated @RequestBody SensorThresholdUpdateRequest sensorThresholdUpdateRequest){
        SensorResponse sensorResponse = sensorService.update(sensorThresholdUpdateRequest);
        return ResponseEntity.ok(sensorResponse);
    }

    @DeleteMapping("/{sensor-id}")
    ResponseEntity<Void> deleteThreshold(@PathVariable("sensor-id") long sensorNo){
        sensorService.delete(sensorNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



}
