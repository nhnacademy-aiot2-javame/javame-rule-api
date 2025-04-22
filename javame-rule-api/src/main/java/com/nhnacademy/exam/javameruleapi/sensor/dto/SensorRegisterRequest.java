package com.nhnacademy.exam.javameruleapi.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class SensorRegisterRequest {

    private final String companyDomain;

    private final String sensorId;

}
