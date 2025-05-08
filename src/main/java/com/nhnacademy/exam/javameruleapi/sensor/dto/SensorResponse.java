package com.nhnacademy.exam.javameruleapi.sensor.dto;

import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 *
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SensorResponse {

    private long sensorNo;

    private String companyDomain;

    private String sensorId;


}
