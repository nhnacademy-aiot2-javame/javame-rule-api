package com.nhnacademy.exam.javameruleapi.dataType.service;

import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeRegisterRequest;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeResponse;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeUpdateRequest;

public interface DataTypeService {

    DataTypeResponse register(DataTypeRegisterRequest dataTypeRegisterRequest);

    DataTypeResponse getDataType(long dataTypeNo);

    DataTypeResponse update(DataTypeUpdateRequest dataTypeUpdateRequest);

    void delete(long dataTypeNo);
}
