package com.nhnacademy.exam.javameruleapi.dataType.dto;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataTypeRegisterRequest {

    /**
     *
     */
    private final Sensor sensor;

    /**
     *  데이터 타입 이름 ex) "temperature", "humidity"
     */
    private final String dataTypeName;

    /**
     * 최소 임계값
     */
    private final Double minThreshold;

    /**
     *  최대 임계값
     */
    private final Double maxThreshold;

}
