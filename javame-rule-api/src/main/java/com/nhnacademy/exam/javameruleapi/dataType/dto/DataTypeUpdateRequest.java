package com.nhnacademy.exam.javameruleapi.dataType.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataTypeUpdateRequest {

    /**
     *
     */
    private final String dataTypeName;

    /**
     *
     */
    private final Double minThreshold;

    /**
     *
     */
    private final Double maxThreshold;
}
