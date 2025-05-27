package com.nhnacademy.exam.javameruleapi.sensorData.repository;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
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
public class SensorDataRepositoryTest {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorRepository sensorRepository;

    private List<Sensor> sensors = new ArrayList<>();

    private List<SensorData> sensorDatas = new ArrayList<>();

    @BeforeEach
    public void setUp() {


        int evenIndex = 0; //짝수 센서 인덱스 추적

        // 센서 5개를 미리 만들어 놓음.
        for (int i = 1; i < 6; i++) {
            Sensor sensor = new Sensor("sample_domain%d".formatted(i), "temperature_sensor%d".formatted(i));

            sensorRepository.save(sensor);
            sensors.add(sensor);


            //짝수 센서에만 센서 데이터 등록해 놓았음.
            if (i % 2 == 0) {
                String sensorDataName = (evenIndex % 2 == 0) ? "lora1" : "lora2";
                SensorData sensorData = new SensorData(sensor, "24e124fffef5dccc", "입구", sensorDataName, 89.2, 99.9);

                evenIndex++;

                sensorDataRepository.save(sensorData);
                sensorDatas.add(sensorData);
            }
        }
    }

    @Test
    @DisplayName("센서 데이터 등록")
    void register() {
        //첫번째 센서에 센서 데이터  추가.
        Optional<Sensor> optionalSensor = sensorRepository.getBySensorNo(sensors.get(0).getSensorNo());
        Assertions.assertTrue(optionalSensor.isPresent());
        Sensor foundSensor = optionalSensor.get();


        SensorData sensorData = new SensorData(foundSensor, "24e124fffef79384", "입구", "lora2", 73.7, 99.0);

        sensorDataRepository.save(sensorData);
        sensorDatas.add(sensorData);
        sensorRepository.save(foundSensor);

        Optional<SensorData> optionalSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorData.getSensorDataNo()); //sensorData.getDataTypeNo()가 null일 수 도 있음..
        Assertions.assertTrue(optionalSensorData.isPresent());

        SensorData sensorData1 = optionalSensorData.get();

        Assertions.assertAll(() -> Assertions.assertEquals(sensorData.getSensorDataNo(), sensorData1.getSensorDataNo()), () -> Assertions.assertEquals("24e124fffef79384", sensorData1.getSensorDataGateway()), () -> Assertions.assertEquals("입구", sensorData1.getSensorDataLocation()), () -> Assertions.assertEquals("lora2", sensorData1.getSensorDataName()), () -> Assertions.assertEquals(73.7, sensorData1.getMinThreshold()), () -> Assertions.assertEquals(99.0, sensorData1.getMaxThreshold()));
    }


    @Test
    @DisplayName("센서 데이터  조회 - 데이터 이름")
    void get() {
        Boolean data = sensorDataRepository.existsDataTypeBySensorDataName("lora1");
        Assertions.assertTrue(data);
    }

    @Test
    @DisplayName("센서 데이터 조회 - 데이터 번호")
    void getDataTypeByNo() {
        Optional<SensorData> optional = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(1).getSensorDataNo());
        Assertions.assertTrue(optional.isPresent());

        SensorData sensorData = optional.get();
        Assertions.assertAll(() -> Assertions.assertEquals(sensorDatas.get(1).getSensorDataNo(), sensorData.getSensorDataNo()), () -> Assertions.assertEquals(sensorDatas.get(1).getSensorDataLocation(), sensorData.getSensorDataLocation()), () -> Assertions.assertEquals(sensorDatas.get(1).getSensorDataGateway(), sensorData.getSensorDataGateway()), () -> Assertions.assertEquals(sensorDatas.get(1).getSensorDataName(), sensorData.getSensorDataName()), () -> Assertions.assertEquals(sensorDatas.get(1).getMinThreshold(), sensorData.getMinThreshold()), () -> Assertions.assertEquals(sensorDatas.get(1).getMaxThreshold(), sensorData.getMaxThreshold()));
    }

    @Test
    @DisplayName("센서 데이터 리스트 조회 - 센서 번호")
    void getSenssorDatasBySensorNo() {
        List<SensorData> sensorDatas1 = sensorDataRepository.getSensorDataBySensor_SensorNo(sensors.get(1).getSensorNo());
        Assertions.assertEquals(1, sensorDatas1.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals("lora1", sensorDatas.get(0).getSensorDataName()),
                () -> Assertions.assertEquals("입구", sensorDatas.get(0).getSensorDataLocation()),
                () -> Assertions.assertEquals("24e124fffef5dccc", sensorDatas.get(0).getSensorDataGateway()),
                () -> Assertions.assertEquals(89.2, sensorDatas.get(0).getMinThreshold()),
                () -> Assertions.assertEquals(99.9, sensorDatas.get(0).getMaxThreshold()));
    }


    @Test
    @DisplayName("센서 데이터 업데이트")
    void update() {
        Optional<SensorData> optionalSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(1).getSensorDataNo());
        Assertions.assertTrue(optionalSensorData.isPresent());

        SensorData targetSensorData = optionalSensorData.get();
        targetSensorData.update("humidity", "출구", "lora999", 3.3, 89.2

        );


        Assertions.assertAll(
                () -> Assertions.assertEquals(targetSensorData.getSensor().getSensorNo(), sensors.get(3).getSensorNo()),
                () -> Assertions.assertEquals("lora999", targetSensorData.getSensorDataName()),
                () -> Assertions.assertEquals(3.3, targetSensorData.getMinThreshold()),
                () -> Assertions.assertEquals(89.2, targetSensorData.getMaxThreshold())
        );
    }


    @Test
    @DisplayName("센서 데이터 삭제")
    void delete() {
        Optional<SensorData> optionalSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(0).getSensorDataNo());
        Assertions.assertTrue(optionalSensorData.isPresent());
        SensorData targetSensorData = optionalSensorData.get();

        sensorDataRepository.delete(targetSensorData);
        Optional<SensorData> deleted = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(0).getSensorDataNo());
        Assertions.assertTrue(deleted.isEmpty());
    }


}


