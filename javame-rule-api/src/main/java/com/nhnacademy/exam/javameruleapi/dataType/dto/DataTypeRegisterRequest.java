package com.nhnacademy.exam.javameruleapi.dataType.dto;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataTypeRegisterRequest {

    private final Sensor sensor;

    private final String dataTypeName;

    private final Double threshold;

}
