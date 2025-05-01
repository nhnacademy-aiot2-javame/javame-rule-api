package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorDataUpdateRequest {

    /**
     *
     */
    private final String sensorDataName;

    /**
     *
     */
    private final Double minThreshold;

    /**
     *
     */
    private final Double maxThreshold;
}
