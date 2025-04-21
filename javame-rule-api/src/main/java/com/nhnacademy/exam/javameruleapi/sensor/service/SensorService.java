package com.nhnacademy.exam.javameruleapi.sensor.service;

import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorThresholdRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorThresholdUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerThresholdUpdateRequest;
import org.springframework.stereotype.Service;

public interface SensorService {

    SensorResponse register(SensorThresholdRegisterRequest sensorThresholdRegisterRequest);

    SensorResponse getSensorThreshold(long sensorNo);

    SensorResponse update(SensorThresholdUpdateRequest sensorThresholdUpdateRequest);

    Void delete(long sensorNo);


}
