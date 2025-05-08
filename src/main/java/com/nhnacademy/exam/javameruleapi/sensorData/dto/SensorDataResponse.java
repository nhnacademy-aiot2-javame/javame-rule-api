package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorDataResponse {

    private String sensorId;

    private long sensorDataNo;

    private String sensorDataName;

    private Double minThreshold;

    private Double maxThreshold;

    private String companyDomain;

}
