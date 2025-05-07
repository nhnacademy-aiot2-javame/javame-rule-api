//package com.nhnacademy.exam.javameruleapi.sensor.service;
//
//import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.AlreadySensorExistException;
//import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.SensorNotExistException;
//import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
//import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
//import com.nhnacademy.exam.javameruleapi.sensor.service.Impl.SensorServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@ActiveProfiles("test")
//@ExtendWith(MockitoExtension.class)
//public class SensorServiceImplTest {
//
//    @Mock
//    SensorRepository sensorRepository;
//
//    @InjectMocks
//    SensorServiceImpl sensorServiceImpl;
//
//    private SensorRegisterRequest sensorRegisterRequest;
//    private Sensor sensor;
//    private List<Sensor> sensors;
//
//
//    @BeforeEach
//    void setUp(){
//        sensorRegisterRequest = new SensorRegisterRequest("nhn_company", "234567e");
//        sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
//        ReflectionTestUtils.setField(sensor, "sensorNo", 1L);
//    }
//
//    @Test
//    @DisplayName("센서 등록")
//    void register() {
//
//        Mockito.when(sensorRepository.existsSensorBySensorIdAndCompanyDomain(Mockito.anyString(), Mockito.anyString()))
//                .thenReturn(false);
//        Mockito.when(sensorRepository.save(Mockito.any(Sensor.class))).thenReturn(sensor);
//
//        SensorResponse sensorResponse = sensorServiceImpl.register(sensorRegisterRequest);
//        log.debug("sensorResponse:{}",sensorResponse);
//
//        Mockito.verify(sensorRepository, Mockito.times(1)).existsSensorBySensorIdAndCompanyDomain(Mockito.anyString(), Mockito.anyString());
//
//        Assertions.assertNotNull(sensorResponse.getSensorNo());
//        Assertions.assertAll(
//                ()-> Assertions.assertEquals(1, sensorResponse.getSensorNo()),
//                ()-> Assertions.assertEquals("nhn_company", sensorResponse.getCompanyDomain()),
//                ()-> Assertions.assertEquals("234567e", sensorResponse.getSensorId())
//        );
//    }
//
//    @Test
//    @DisplayName("센서 등록 - 센서아이디+회사도메인 중복체크")
//    void register_exception_case1(){
//
//        Mockito.when(sensorRepository.existsSensorBySensorIdAndCompanyDomain(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
//
//            Assertions.assertThrows(AlreadySensorExistException.class, ()-> {
//                sensorServiceImpl.register(sensorRegisterRequest);
//            });
//
//            Mockito.verify(sensorRepository, Mockito.times(1))
//                    .existsSensorBySensorIdAndCompanyDomain(Mockito.anyString(),Mockito.anyString());
//        }
//
//
//
//    @Test
//    @DisplayName("센서 번호로 센서 조회")
//    void getSensor(){
//        Mockito.when(sensorRepository.getSensorBySensorNo(Mockito.anyLong())).thenReturn(Optional.of(sensor));
//
//        SensorResponse sensorResponse = sensorServiceImpl.getSensor(sensor.getSensorNo());
//
//        Mockito.verify(sensorRepository, Mockito.times(1)).getSensorBySensorNo(Mockito.anyLong());
//
//        Assertions.assertNotNull(sensorResponse.getSensorNo());
//        Assertions.assertAll(
//                ()-> Assertions.assertEquals(1, sensorResponse.getSensorNo()),
//                ()-> Assertions.assertEquals("nhn_company", sensorResponse.getCompanyDomain()),
//                ()-> Assertions.assertEquals("234567e", sensorResponse.getSensorId())
//        );
//    }
//
//    @Test
//    @DisplayName("센서 번호로 센서 조회 - 센서 번호 중복 체크")
//    void getSensor_exception_case1(){
//        Mockito.when(sensorRepository.getSensorBySensorNo(Mockito.anyLong())).thenReturn(Optional.empty());
//
//       Assertions.assertThrows(SensorNotExistException.class, ()->{
//           sensorServiceImpl.getSensor(sensor.getSensorNo());
//       });
//
//        Mockito.verify(sensorRepository, Mockito.times(1)).getSensorBySensorNo(Mockito.anyLong());
//    }
//
//
//    @Test
//    @DisplayName("회사 도메인으로 센서 리스트 조회")
//    void getSensors(){
//        sensors = new ArrayList<>();
//        sensors.add(sensor);
//
//        SensorRegisterRequest sensorRegisterRequest = new SensorRegisterRequest("nhn_company", "67890uuu");
//        Sensor sensor2 = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
//        sensors.add(sensor2);
//
//        Mockito.when(sensorRepository.getSensorsByCompanyDomain(Mockito.anyString())).thenReturn(Optional.of(sensors));
//
//        List<SensorResponse> sensorResponses = sensorServiceImpl.getSensors(sensor.getCompanyDomain());
//        log.debug("sensorResponses:{}", sensorResponses);
//        Mockito.verify(sensorRepository, Mockito.times(1)).getSensorsByCompanyDomain(Mockito.anyString());
//
//        Assertions.assertNotNull(sensors, "해당 도메인으로 조회된 센서가 없습니다.");
//        Assertions.assertEquals(2, sensors.size(), "센서 리스트 길이가 2가 아닙니다.");
//
//        sensorResponses.forEach(sensorResponse -> {
//            Assertions.assertEquals("nhn_company", sensorResponse.getCompanyDomain(), "센서의 도메인이 nhn_company가 아닙니다.");
//        });
//    }
//
//
//    @Test
//    @DisplayName("회사 도메인으로 센서 리스트 조회 - 회사 도메인 중복 체크")
//    void getSensors_exception_case1(){
//
//        Mockito.when(sensorRepository.getSensorsByCompanyDomain(Mockito.anyString())).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(SensorNotExistException.class, ()-> sensorServiceImpl.getSensors(sensor.getCompanyDomain()));
//
//        Mockito.verify(sensorRepository, Mockito.times(1)).getSensorsByCompanyDomain(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("센서 삭제")
//    void delete(){
//        Mockito.when(sensorRepository.getSensorBySensorNo(Mockito.anyLong())).thenReturn(Optional.of(sensor));
//        Mockito.doNothing().when(sensorRepository).delete(Mockito.any(Sensor.class));
//
//        Void sensorResponse = sensorServiceImpl.delete(sensor.getSensorNo());
//
//        Mockito.verify(sensorRepository, Mockito.times(1)).getSensorBySensorNo(Mockito.anyLong());
//        Mockito.verify(sensorRepository, Mockito.times(1)).delete(Mockito.any(Sensor.class));
//
//        Assertions.assertNull(sensorResponse);
//    }
//
//
//
//
//}
