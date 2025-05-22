package com.nhnacademy.exam.javameruleapi.sensorData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SensorDataRegisterRequest {

    /**
     * 데이터 타입 이름 ex) "temperature", "humidity".
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
     */
    private Double minThreshold;

    /**
     * 최대 임계값.
     */
    private Double maxThreshold;


}
