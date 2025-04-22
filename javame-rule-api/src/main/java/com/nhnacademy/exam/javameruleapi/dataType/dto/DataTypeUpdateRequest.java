package com.nhnacademy.exam.javameruleapi.dataType.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataTypeUpdateRequest {

    private final long dataTypeNo;

    private final long sensorNo;

    private final String dataTypeName;

    private final Double threshold;
}
