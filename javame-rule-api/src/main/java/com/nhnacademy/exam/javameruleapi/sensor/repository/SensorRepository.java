package com.nhnacademy.exam.javameruleapi.sensor.repository;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Boolean existsSensorBySensorId(String sensorId);

    Sensor getSensorBySensorNo(long sensorNo);

    Sensor update(Sensor sensor);
}
