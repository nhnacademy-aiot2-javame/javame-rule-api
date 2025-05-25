package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 센서 데이터 수정 요청을 위한 DTO
 * <p>
 * 기존 센서 데이터의 이름 및 임계값 범위를 수정할 때 사용됩니다.
 */
@AllArgsConstructor
@Getter
public class SensorDataUpdateRequest {


    /**
     * 센서 데이터 location (예 : 입구)
     */
    private String sensorDataLocation;

    /**
     * 센서 데이터 게이트웨이 (예 : 24e124fffef79114)
     */
    private String sensorDataGateway;

    /**
     * 센서 데이터 이름.
     * 예: lora 등
     */
    private final String sensorDataName;


    /**
     * 최소 임계값.
     * 센서 데이터의 새로운 최소 임계값.
     */
    private final Double minThreshold;

    /**
     * 최대 임계값.
     * 센서 데이터의 새로운 최대 임계값.
     */
    private final Double maxThreshold;
}
