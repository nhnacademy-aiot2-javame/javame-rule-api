package com.nhnacademy.exam.javameruleapi.sensorData.repository;

import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    Boolean existsDataTypeByDataTypeName(String sensorDataName);

    SensorData save(SensorData sensorData);

    Optional<SensorData> getSensorDataBySensorDataNo(long sensorDataNo);

    void delete(SensorData sensorData);

    // 데이터 타입 조회 시, dataTypeNo로 조회,
    // Sensor 찾아서 그 센서의 dataType조회.
}
