package com.nhnacademy.exam.javameruleapi.sensorData.service;

import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;

public interface SensorDataService {

    SensorDataResponse register(String sensorId, SensorDataRegisterRequest sensorDataRegisterRequest);

    SensorDataResponse getSensorDataBySensorDataNo(long sensorDataNo);

    SensorDataResponse getSensorDataBySensorId(String sensorId);

    SensorDataResponse update(long dataTypeNo, SensorDataUpdateRequest sensorDataUpdateRequest);

    Void delete(long dataTypeNo);
}
