package com.nhnacademy.exam.javameruleapi.dataType.repository;

import com.nhnacademy.exam.javameruleapi.dataType.domain.DataType;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Slf4j
@ActiveProfiles("test")
public class DataTypeRepositoryTest {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private SensorRepository sensorRepository;

    private List<Sensor> sensors = new ArrayList<>();

    private List<DataType> dataTypes = new ArrayList<>();

    @BeforeEach
    public void setUp() {


        int evenIndex = 0; //짝수 센서 인덱스 추적

        // 센서 5개를 미리 만들어 놓음.
        for (int i = 1; i < 6; i++) {
            Sensor sensor = new Sensor(
                    "sample_domain%d".formatted(i),
                    "temperature_sensor%d".formatted(i)
            );

            sensorRepository.save(sensor);
            sensors.add(sensor);

            //짝수 센서에만 dataType 등록해 놓았음.
            if (i % 2 == 0) {
                String dataTypeName = (evenIndex % 2 == 0) ? "temperature" : "humidity";
                DataType dataType = new DataType(
                        sensor,
                        dataTypeName,
                        44.4,
                        99.9
                );

                evenIndex++;

                dataTypeRepository.save(dataType);
                dataTypes.add(dataType);
            }
        }
    }

    @Test
    @DisplayName("데이터 타입 등록")
    void register() {
        //첫번째 센서에 데이터 타입 추가.
        Optional<Sensor> optionalSensor = sensorRepository.getSensorBySensorNo(sensors.get(0).getSensorNo());
        Assertions.assertTrue(optionalSensor.isPresent());
        Sensor foundSensor = optionalSensor.get();


        DataType dataType = new DataType(
                foundSensor,
                "humidity",
                33.0,
                73.7
        );

        dataTypeRepository.save(dataType);
        dataTypes.add(dataType);
        sensorRepository.save(foundSensor);

        Optional<DataType> optionalDataType = dataTypeRepository.getDataTypeByDataTypeNo(dataType.getDataTypeNo()); //dataType.getDataTypeNo()가 null일 수 도 있음..
        Assertions.assertTrue(optionalDataType.isPresent());

        DataType dataType1 = optionalDataType.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(dataType.getDataTypeNo(), dataType1.getDataTypeNo()),
                () -> Assertions.assertEquals(foundSensor, dataType1.getSensor()),
                () -> Assertions.assertEquals("humidity", dataType1.getDataTypeName()),
                () -> Assertions.assertEquals(33.0, dataType1.getMinThreshold()),
                () -> Assertions.assertEquals(73.7, dataType1.getMaxThreshold())
        );
    }


    @Test
    @DisplayName("데이터 타입 조회 - 이름")
    void get(){
        Boolean data = dataTypeRepository.existsDataTypeByDataTypeName("temperature");
        Assertions.assertTrue(data);
 }

    @Test
    @DisplayName("데이터 타입 조회 - No")
    void getDataTypeByNo(){
        Optional<DataType> optional = dataTypeRepository.getDataTypeByDataTypeNo(dataTypes.get(1).getDataTypeNo());
        Assertions.assertTrue(optional.isPresent());

        DataType dataType = optional.get();
        Assertions.assertAll(
                ()-> Assertions.assertEquals(dataTypes.get(1).getSensor() ,dataType.getSensor()),
                ()-> Assertions.assertEquals(dataTypes.get(1).getDataTypeName(), dataType.getDataTypeName()),
                ()-> Assertions.assertEquals(dataTypes.get(1).getMinThreshold(), dataType.getMinThreshold()),
                ()-> Assertions.assertEquals(dataTypes.get(1).getMaxThreshold(), dataType.getMaxThreshold()),
                ()-> Assertions.assertEquals(dataTypes.get(1).getDataTypeNo(), dataType.getDataTypeNo())
        );
    }

    @Test
    @DisplayName("데이터 타입 업데이트")
    void update(){
        Optional<DataType> optional = dataTypeRepository.getDataTypeByDataTypeNo(dataTypes.get(1).getDataTypeNo());
        Assertions.assertTrue(optional.isPresent());

        DataType target = optional.get();
        target.update("humidity",
                0.2,
                9.4
        );

        Assertions.assertAll(
                ()-> Assertions.assertEquals(target.getSensor(), sensors.get(3)),
                ()-> Assertions.assertEquals(target.getDataTypeName(), "humidity"),
                ()-> Assertions.assertEquals(target.getMinThreshold(), 0.2),
                ()-> Assertions.assertEquals(target.getMaxThreshold(), 9.4)
        );
        }


        @Test
        @DisplayName("삭제")
        void delete(){
        Optional<DataType> optional = dataTypeRepository.getDataTypeByDataTypeNo(dataTypes.get(0).getDataTypeNo());
        Assertions.assertTrue(optional.isPresent());
        DataType target = optional.get();

        dataTypeRepository.delete(target);
        Optional<DataType> deleted = dataTypeRepository.getDataTypeByDataTypeNo(dataTypes.get(0).getDataTypeNo());
        Assertions.assertTrue(deleted.isEmpty());
    }



}


