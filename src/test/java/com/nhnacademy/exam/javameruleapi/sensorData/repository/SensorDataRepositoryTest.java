//package com.nhnacademy.exam.javameruleapi.sensorData.repository;
//
//import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
//import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
//import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@DataJpaTest
//@Slf4j
//@ActiveProfiles("test")
//public class SensorDataRepositoryTest {
//
//    @Autowired
//    private SensorDataRepository sensorDataRepository;
//
//    @Autowired
//    private SensorRepository sensorRepository;
//
//    private List<Sensor> sensors = new ArrayList<>();
//
//    private List<SensorData> sensorDatas = new ArrayList<>();
//
//    @BeforeEach
//    public void setUp() {
//
//
//        int evenIndex = 0; //짝수 센서 인덱스 추적
//
//        // 센서 5개를 미리 만들어 놓음.
//        for (int i = 1; i < 6; i++) {
//            Sensor sensor = new Sensor(
//                    "sample_domain%d".formatted(i),
//                    "temperature_sensor%d".formatted(i)
//            );
//
//            sensorRepository.save(sensor);
//            sensors.add(sensor);
//
//            //짝수 센서에만 dataType 등록해 놓았음.
//            if (i % 2 == 0) {
//                String dataTypeName = (evenIndex % 2 == 0) ? "temperature" : "humidity";
//                SensorData sensorData = new SensorData(
//                        sensor,
//                        dataTypeName,
//                        44.4,
//                        99.9
//                );
//
//                evenIndex++;
//
//                sensorDataRepository.save(sensorData);
//                sensorDatas.add(sensorData);
//            }
//        }
//    }
//
//    @Test
//    @DisplayName("데이터 타입 등록")
//    void register() {
//        //첫번째 센서에 데이터 타입 추가.
//        Optional<Sensor> optionalSensor = sensorRepository.getSensorBySensorNo(sensors.get(0).getSensorNo());
//        Assertions.assertTrue(optionalSensor.isPresent());
//        Sensor foundSensor = optionalSensor.get();
//
//
//        SensorData sensorData = new SensorData(
//                foundSensor,
//                "humidity",
//                33.0,
//                73.7
//        );
//
//        sensorDataRepository.save(sensorData);
//        sensorDatas.add(sensorData);
//        sensorRepository.save(foundSensor);
//
//        Optional<SensorData> optionalSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorData.getSensorDataNo()); //sensorData.getDataTypeNo()가 null일 수 도 있음..
//        Assertions.assertTrue(optionalSensorData.isPresent());
//
//        SensorData sensorData1 = optionalSensorData.get();
//
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(sensorData.getSensorDataNo(), sensorData1.getSensorDataNo()),
//                () -> Assertions.assertEquals(foundSensor, sensorData1.getSensor()),
//                () -> Assertions.assertEquals("humidity", sensorData1.getSensorDataName()),
//                () -> Assertions.assertEquals(33.0, sensorData1.getMinThreshold()),
//                () -> Assertions.assertEquals(73.7, sensorData1.getMaxThreshold())
//        );
//    }
//
//
//    @Test
//    @DisplayName("데이터 타입 조회 - 이름")
//    void get(){
//        Boolean data = sensorDataRepository.existsDataTypeByDataTypeName("temperature");
//        Assertions.assertTrue(data);
// }
//
//    @Test
//    @DisplayName("데이터 타입 조회 - No")
//    void getDataTypeByNo(){
//        Optional<SensorData> optional = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(1).getSensorDataNo());
//        Assertions.assertTrue(optional.isPresent());
//
//        SensorData sensorData = optional.get();
//        Assertions.assertAll(
//                ()-> Assertions.assertEquals(sensorDatas.get(1).getSensor() ,sensorData.getSensor()),
//                ()-> Assertions.assertEquals(sensorDatas.get(1).getSensorDataName(), sensorData.getSensorDataName()),
//                ()-> Assertions.assertEquals(sensorDatas.get(1).getMinThreshold(), sensorData.getMinThreshold()),
//                ()-> Assertions.assertEquals(sensorDatas.get(1).getMaxThreshold(), sensorData.getMaxThreshold()),
//                ()-> Assertions.assertEquals(sensorDatas.get(1).getSensorDataNo(), sensorData.getSensorDataNo())
//        );
//    }
//
//    @Test
//    @DisplayName("데이터 타입 업데이트")
//    void update(){
//        Optional<SensorData> optionalSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(1).getSensorDataNo());
//        Assertions.assertTrue(optionalSensorData.isPresent());
//
//        SensorData targetSensorData = optionalSensorData.get();
//        targetSensorData.update("humidity",
//                0.2,
//                9.4
//        );
//
//        Assertions.assertAll(
//                ()-> Assertions.assertEquals(targetSensorData.getSensor(), sensors.get(3)),
//                ()-> Assertions.assertEquals(targetSensorData.getSensorDataName(), "humidity"),
//                ()-> Assertions.assertEquals(targetSensorData.getMinThreshold(), 0.2),
//                ()-> Assertions.assertEquals(targetSensorData.getMaxThreshold(), 9.4)
//        );
//        }
//
//
//        @Test
//        @DisplayName("삭제")
//        void delete(){
//        Optional<SensorData> optionalSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(0).getSensorDataNo());
//        Assertions.assertTrue(optionalSensorData.isPresent());
//        SensorData targetSensorData = optionalSensorData.get();
//
//        sensorDataRepository.delete(targetSensorData);
//        Optional<SensorData> deleted = sensorDataRepository.getSensorDataBySensorDataNo(sensorDatas.get(0).getSensorDataNo());
//        Assertions.assertTrue(deleted.isEmpty());
//    }
//
//
//
//}
//
//
