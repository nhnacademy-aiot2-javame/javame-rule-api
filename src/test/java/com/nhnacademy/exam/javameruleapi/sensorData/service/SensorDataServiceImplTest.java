package com.nhnacademy.exam.javameruleapi.sensorData.service;

import com.nhnacademy.exam.javameruleapi.sensorData.common.Exception.AlreadySensorDataExistException;
import com.nhnacademy.exam.javameruleapi.sensorData.common.Exception.SensorDataNotExistException;
import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.repository.SensorDataRepository;
import com.nhnacademy.exam.javameruleapi.sensorData.service.Impl.SensorDataServiceImpl;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class SensorDataServiceImplTest {


    @Mock
    SensorDataRepository sensorDataRepository;


    @InjectMocks
    SensorDataServiceImpl sensorDataServiceImpl;

    private SensorRegisterRequest sensorRegisterRequest;
    private Sensor sensor;
    private SensorDataRegisterRequest sensorDataRegisterRequest;
    private SensorData sensorData;


    @BeforeEach
    void setUp(){
        sensorRegisterRequest = new SensorRegisterRequest("nhn_company", "123456789");

        sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
        // DB에 저장된게 아니라서 sensorNo가 null일 수 있음.
        ReflectionTestUtils.setField(sensor, "sensorNo", 1L);

        sensorDataRegisterRequest = new SensorDataRegisterRequest(
                "123456789", "temperature",
                33.2, 89.0, "nhn_academy");

        sensorData = new SensorData(
                sensorDataRegisterRequest.getSensorId(),
                sensorDataRegisterRequest.getSensorDataName(),
                sensorDataRegisterRequest.getMinThreshold(),
                sensorDataRegisterRequest.getMaxThreshold(),
                sensorDataRegisterRequest.getCompanyDomain()
        );

        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 1L);
    }

    @Test
    @DisplayName("데이터 타입 등록")
    void register() {


        Mockito.when(sensorDataRepository.existsDataTypeBySensorDataName(sensorDataRegisterRequest.getSensorDataName())).thenReturn(false);
        Mockito.when(sensorDataRepository.save(Mockito.any(SensorData.class))).thenReturn(sensorData);


        SensorDataResponse sensorDataResponse = sensorDataServiceImpl.register(sensor.getSensorId(),sensorDataRegisterRequest);
        log.debug("dataTypeResponse:{}", sensorDataResponse);

        Mockito.verify(sensorDataRepository, Mockito.times(1)).save(Mockito.any(SensorData.class));
        Mockito.verify(sensorDataRepository, Mockito.times(1)).existsDataTypeBySensorDataName((Mockito.anyString()));

        Assertions.assertAll(
                ()-> Assertions.assertNotNull(sensorDataResponse.getSensorDataNo()),
                ()-> Assertions.assertEquals("temperature", sensorDataResponse.getSensorDataName()),
                ()-> Assertions.assertEquals(33.2, sensorDataResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(89.0, sensorDataResponse.getMaxThreshold())
        );
    }

    @Test
    @DisplayName("데이터 타입 등록 - 데이터 타입 이름 중복 체크")
    void register_exception_case1(){
        Mockito.when(sensorDataRepository.existsDataTypeBySensorDataName(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(AlreadySensorDataExistException.class, ()-> {
            sensorDataServiceImpl.register(sensor.getSensorId(), sensorDataRegisterRequest);
        });

        Mockito.verify(sensorDataRepository, Mockito.times(1)).existsDataTypeBySensorDataName(Mockito.anyString());
        Mockito.verify(sensorDataRepository, Mockito.never()).save(Mockito.any(SensorData.class));
    }


    @Test
    @DisplayName("데이터 타입 조회")
    void getSensorData() {

        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 1L);
        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(Optional.of(sensorData));

        SensorDataResponse sensorDataResponse = sensorDataServiceImpl.getSensorDataBySensorDataNo(sensorData.getSensorDataNo());

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());

        Assertions.assertAll(
                ()-> Assertions.assertNotNull(sensorDataResponse),
                ()-> Assertions.assertEquals(sensor.getSensorId(), sensorDataResponse.getSensorId()),
                ()-> Assertions.assertEquals(1, sensorDataResponse.getSensorDataNo()),
                ()-> Assertions.assertEquals("temperature", sensorDataResponse.getSensorDataName()),
                ()-> Assertions.assertEquals(33.2, sensorDataResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(89.0, sensorDataResponse.getMaxThreshold())
        );

    }

    @Test
    @DisplayName("데이터 타입 조회 - 데이터 타입 번호 중복 체크")
    void getSensorData_exception_case1(){

        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 1L);
        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(SensorDataNotExistException.class, () -> {
            sensorDataServiceImpl.getSensorDataBySensorDataNo(1);
        });

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());

    }

    @Test
    @DisplayName("데이터 타입 수정")
    void update(){

        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(Optional.of(sensorData));
        Mockito.when(sensorDataRepository.save(Mockito.any(SensorData.class))).thenReturn(sensorData);

        SensorDataUpdateRequest sensorDataUpdateRequest = new SensorDataUpdateRequest("humidity", 33.2, 90.4);

        SensorDataResponse sensorDataResponse = sensorDataServiceImpl.update(1, sensorDataUpdateRequest);
        log.debug("dataTypeResponse:{}", sensorDataResponse);

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());

        Assertions.assertAll(
                ()-> Assertions.assertNotNull(sensorDataResponse.getSensorDataNo()),
                ()-> Assertions.assertEquals(sensor.getSensorId(), sensorDataResponse.getSensorId()),
                ()-> Assertions.assertEquals(1, sensorDataResponse.getSensorDataNo()),
                ()-> Assertions.assertEquals("humidity", sensorDataResponse.getSensorDataName()),
                ()-> Assertions.assertEquals(33.2, sensorDataResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(90.4, sensorDataResponse.getMaxThreshold())
        );
    }


    @Test
    @DisplayName("데이터 타입 삭제")
    void delete() {

        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(Optional.of(sensorData));
        Mockito.doNothing().when(sensorDataRepository).delete(Mockito.any(SensorData.class));

        Void sensorDataResponse = sensorDataServiceImpl.delete(sensorData.getSensorDataNo());

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());
        Mockito.verify(sensorDataRepository, Mockito.times(1)).delete(Mockito.any(SensorData.class));

        Assertions.assertNull(sensorDataResponse);
    }

}
