package com.nhnacademy.exam.javameruleapi.sensor.repository;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Boolean existsSensorBySensorId(String sensorId);

    Boolean existsSensorByCompanyDomain(String companyDomain);

    Optional<Sensor> getSensorBySensorNo(long sensorNo);

    Optional<Sensor> getSensorBySensorId(String sensorId);

    Optional<List<Sensor>> getSensorsByCompanyDomain(String companyDomain);

    boolean existsSensorBySensorIdAndCompanyDomain(String sensorId, String companyDomain);
}
