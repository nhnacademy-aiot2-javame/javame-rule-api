package com.nhnacademy.exam.javameruleapi.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorUpdateRequest {

    private final long sensorNo;

    private final String companyDomain;

    private final String sensorId;
}
