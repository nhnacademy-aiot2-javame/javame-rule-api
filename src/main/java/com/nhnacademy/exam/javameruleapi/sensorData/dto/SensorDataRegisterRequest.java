package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorDataRegisterRequest {

    /**
     *
     */
    private String sensorId;

    /**
     *  데이터 타입 이름 ex) "temperature", "humidity".
     */
    private String sensorDataName;

    /**
     * 최소 임계값.
     */
    private Double minThreshold;

    /**
     *  최대 임계값
     */
    private Double maxThreshold;

    /**
     *
     */
    private String companyDomain;

}
