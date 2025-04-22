package com.nhnacademy.exam.javameruleapi.dataType.repository;

import com.nhnacademy.exam.javameruleapi.dataType.domain.DataType;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataTypeRepository extends JpaRepository<DataType, Long> {

    Boolean findDataTypeByDataTypeName(String dataTypeName);

    DataType save(DataType dataType);

    DataType getDataTypeByDataTypeNo(long dataTypeNo);

    void delete(DataType dataType);

    void update(DataType foundDataType);
}
