package com.nhnacademy.exam.javameruleapi.dataType.repository;

import com.nhnacademy.exam.javameruleapi.dataType.domain.DataType;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataTypeRepository extends JpaRepository<DataType, Long> {

    Boolean existsDataTypeByDataTypeName(String dataTypeName);

    DataType save(DataType dataType);

    Optional<DataType> getDataTypeByDataTypeNo(long dataTypeNo);

    void delete(DataType dataType);

    // 데이터 타입 조회 시, dataTypeNo로 조회,
    // Sensor 찾아서 그 센서의 dataType조회.
}
