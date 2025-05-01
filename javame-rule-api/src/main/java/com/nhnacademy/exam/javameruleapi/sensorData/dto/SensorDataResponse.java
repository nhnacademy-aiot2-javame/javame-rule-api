package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorDataResponse {

    private final long sensorDataNo;

    private final String sensorDataName;

    private final Double minThreshold;

    private final Double maxThreshold;

}
