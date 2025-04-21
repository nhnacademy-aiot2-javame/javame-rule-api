package com.nhnacademy.exam.javameruleapi.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorThresholdRegisterRequest {

    private final long sensorNo;

    private final String sensorType;

    private final long threshold;

    private final Company company;


}
