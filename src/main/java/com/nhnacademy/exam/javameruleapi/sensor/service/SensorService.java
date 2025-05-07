package com.nhnacademy.exam.javameruleapi.sensor.service;

import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;

import java.util.List;

public interface SensorService {

    SensorResponse register(SensorRegisterRequest sensorRegisterRequest);

    SensorResponse getSensor(long sensorNo);

    List<SensorResponse> getSensors(String companyDomain);

    Void delete(long sensorNo);


}
