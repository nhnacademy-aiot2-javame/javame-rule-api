package com.nhnacademy.exam.javameruleapi.sensor.repository;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@ActiveProfiles("test")
@DataJpaTest
@Transactional
public class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @BeforeEach
    void setUp() {

        List<Sensor> sensors = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Sensor sensor = new Sensor(
                    "sample_domain%d".formatted(i),
                    "temperature_sensor%d".formatted(i)
            );

            sensorRepository.save(sensor);
            sensors.add(sensor);
        }
    }

    @Test
    @DisplayName("센서 등록")
    @Order(1)
    void registerSensor(){
        Sensor sensor = new Sensor(
                "javaMe_domain",
                "humidity_sensor"
        );
        sensorRepository.save(sensor);

        Optional<Sensor> optional = sensorRepository.getSensorBySensorId("humidity_sensor");
        Assertions.assertTrue(optional.isPresent());

        Sensor found = optional.get();

        Assertions.assertAll(
                ()-> Assertions.assertEquals(sensor.getCompanyDomain(), found.getCompanyDomain()),
                ()-> Assertions.assertEquals(sensor.getSensorId(), found.getSensorId())
        );
    }


    @Test
    @DisplayName("센서 조회")
    @Order(2)
    void getSensor(){
        Optional<Sensor> optional = sensorRepository.getSensorBySensorId("temperature_sensor4");
        Assertions.assertTrue(optional.isPresent());
        Sensor found = optional.get();

        Assertions.assertAll(
                ()-> Assertions.assertEquals("sample_domain4", found.getCompanyDomain()),
                ()-> Assertions.assertEquals("temperature_sensor4", found.getSensorId())
        );
    }



    @Test
    @DisplayName("센서 삭제")
    @Order(3)
    void delete(){
        Sensor sensor = new Sensor(
            "sample_domain%d".formatted(10),
            "temperature_sensor%d".formatted(10)
        );
        Sensor savedSensor = sensorRepository.save(sensor);
        long num = savedSensor.getSensorNo();

        Optional<Sensor> optional = sensorRepository.getBySensorNo(num);
        log.error(optional.get().toString());
        Sensor deleteTarget = optional.get();

        sensorRepository.delete(deleteTarget);

        Optional<Sensor> optional2 = sensorRepository.getBySensorNo(num);
        Assertions.assertTrue(optional2.isEmpty());

    }

}
