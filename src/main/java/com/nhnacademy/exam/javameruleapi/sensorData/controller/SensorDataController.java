package com.nhnacademy.exam.javameruleapi.sensorData.controller;

import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.service.SensorDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor-datas")
@RequiredArgsConstructor
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @PostMapping("/{sensor-id}")
    ResponseEntity<SensorDataResponse> registerSensorData(
            @PathVariable("sensor-id") String sensorId,
            @Validated @RequestBody SensorDataRegisterRequest sensorDataRegisterRequest){
       SensorDataResponse sensorDataResponse =  sensorDataService.register(sensorId, sensorDataRegisterRequest);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(sensorDataResponse);
    }

    @GetMapping("/by-no/{sensor-data-no}")
    ResponseEntity<SensorDataResponse> getSensorDataBySensorDataNo(@PathVariable("sensor-data-no") long sensorDataNo){
        SensorDataResponse sensorDataResponse = sensorDataService.getSensorDataBySensorDataNo(sensorDataNo);
        return ResponseEntity
                .ok(sensorDataResponse);
    }

    @GetMapping("/by-id/{sensor-id}")
    ResponseEntity<SensorDataResponse> getSensorDataBySensorId(@PathVariable("sensor-id") String sensorId) {
        SensorDataResponse sensorDataResponse = sensorDataService.getSensorDataBySensorId(sensorId);
        return ResponseEntity
                .ok(sensorDataResponse);
    }

    @PutMapping("/{sensor-data-no}")
    ResponseEntity<SensorDataResponse> updateSensorData(@PathVariable("sensor-data-no") long sensorDataNo, @Validated @RequestBody SensorDataUpdateRequest sensorDataUpdateRequest){
        SensorDataResponse sensorDataResponse = sensorDataService.update(sensorDataNo, sensorDataUpdateRequest);
        return ResponseEntity
                .ok(sensorDataResponse);
    }

    @DeleteMapping("/{sensor-data-no}")
    ResponseEntity<Void> deleteDataType(@PathVariable("sensor-data-no") long sensorDataNo) {
        sensorDataService.delete(sensorDataNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
