package com.nhnacademy.exam.javameruleapi.sensor.service;

import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorUpdateRequest;

public interface SensorService {

    SensorResponse register(SensorRegisterRequest sensorRegisterRequest);

    SensorResponse getSensor(long sensorNo);

    SensorResponse update(SensorUpdateRequest sensorUpdateRequest);

    void delete(long sensorNo);


}
