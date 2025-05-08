package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorDataRegisterRequest {

    /**
     * 센서 고유 ID.
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
     *  최대 임계값.
     */
    private Double maxThreshold;

    /**
     * 센서 데이터가 등록된 회사 도메인.
     */
    private String companyDomain;

}
