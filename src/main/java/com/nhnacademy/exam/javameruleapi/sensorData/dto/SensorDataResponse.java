package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 센서 데이터 응답.
 */
@AllArgsConstructor
@Getter
public class SensorDataResponse {

    /**
     * 센서 번호
     */
    private Long sensorNo;

    /**
     * 회사 도메인.
     * 센서가 등록된 회사의 도메인.
     */
    private String companyDomain;

    /**
     * 센서 데이터 고유 번호.
     */
    private long sensorDataNo;

    /**
     * 센서 데이터 이름.
     * 예: data, test 등
     */
    private String sensorDataName;

    /**
     * 센서 데이터 location (예 : power_meter, server_resources_data)
     */
    private String sensorDataLocation;

    /**
     * 센서 데이터 게이트웨이 (예 : cpu, disk 등등)
     */
    private String sensorDataGateway;

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
     * 생성일자
     * 센서가 생성된 생성일자.
     */
    private LocalDateTime created_at;

}
