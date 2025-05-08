package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 센서 데이터 응답.
 */
@AllArgsConstructor
@Getter
public class SensorDataResponse {

    /**
     * 센서 고유 ID.
     */
    private String sensorId;

    /**
     * 센서 데이터 고유 번호.
     */
    private long sensorDataNo;

    /**
     * 센서 데이터 이름.
     * ex) "temperature", "humidity"등 데이터의 종류를 나타냄.
     */
    private String sensorDataName;

    /**
     * 최소 임계값.
     * 센서 데이터가 이 값보다 작을 경우 경고 조건.
     */
    private Double minThreshold;

    /**
     * 최대 임계값.
     * 센서 데이터가 이 값을 초과할 경우 경고 조건으로 간주.
     */
    private Double maxThreshold;

    /**
     * 회사 도메인.
     * 센서가 등록된 회사의 도메인.
     */
    private String companyDomain;

}
