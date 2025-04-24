package com.nhnacademy.exam.javameruleapi.dataType.repository;

import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Slf4j
@ActiveProfiles("test")
public class DataTypeRepositoryTest {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private SensorRepository sensorRepository;


}


