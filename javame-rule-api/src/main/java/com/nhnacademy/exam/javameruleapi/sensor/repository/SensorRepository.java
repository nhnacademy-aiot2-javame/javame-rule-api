package com.nhnacademy.exam.javameruleapi.sensor.repository;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Boolean existsSensorBySensorId(String sensorId);

    Optional<Sensor> getSensorBySensorNo(long sensorNo);

    Optional<Sensor> getSensorBySensorId(String sensorId);

    Optional<List<Sensor>> getSensorsByCompanyDomain(String companyDomain);

    // 조회는 sensorNo, sensorId 둘다 로 가능한가?
}
