package com.nhnacademy.exam.javameruleapi.sensorData.service;

import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;

public interface SensorDataService {

    SensorDataResponse register(SensorDataRegisterRequest sensorDataRegisterRequest);

    SensorDataResponse getSensorData(long sensorDataNo);

    SensorDataResponse update(long dataTypeNo, SensorDataUpdateRequest sensorDataUpdateRequest);

    Void delete(long dataTypeNo);
}
