package com.nhnacademy.exam.javameruleapi.sensor.service.Impl;

import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorThresholdRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorThresholdUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {
    @Override
    public SensorResponse register(SensorThresholdRegisterRequest sensorThresholdRegisterRequest) {
        return null;
    }

    @Override
    public SensorResponse getSensorThreshold(long sensorNo) {
        return null;
    }

    @Override
    public SensorResponse update(SensorThresholdUpdateRequest sensorThresholdUpdateRequest) {
        return null;
    }

    @Override
    public Void delete(long sensorNo) {
        return null;
    }
}
