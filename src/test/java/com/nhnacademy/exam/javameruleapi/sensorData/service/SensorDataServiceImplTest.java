package com.nhnacademy.exam.javameruleapi.sensorData.service;

import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
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
    @Mock
    SensorRepository sensorRepository;


    @InjectMocks
    SensorDataServiceImpl sensorDataServiceImpl;

    private SensorRegisterRequest sensorRegisterRequest;
    private Sensor sensor;
    private SensorDataRegisterRequest sensorDataRegisterRequest;
    private SensorData sensorData;


    @BeforeEach
    void setUp() {
        sensorRegisterRequest = new SensorRegisterRequest("nhn_company", "123456789");

        sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
        // DB에 저장된게 아니라서 sensorNo가 null일 수 있음.
        ReflectionTestUtils.setField(sensor, "sensorNo", 1L);

        sensorDataRegisterRequest = new SensorDataRegisterRequest(
                "입구", "24e124fffef5ccc",
                "lora", 33.2, 89.0);

        Mockito.when(sensorRepository.getBySensorNo(Mockito.anyLong())).thenReturn(Optional.of(sensor));

        Sensor foundSensor = sensorRepository.getBySensorNo(sensor.getSensorNo())
                .orElseThrow(() -> new SensorDataNotExistException("존재하지 않는 센서 데이터 입니다."));

        sensorData = new SensorData(
                foundSensor,
                sensorDataRegisterRequest.getSensorDataGateway(),
                sensorDataRegisterRequest.getSensorDataLocation(),
                sensorDataRegisterRequest.getSensorDataName(),
                sensorDataRegisterRequest.getMinThreshold(),
                sensorDataRegisterRequest.getMaxThreshold()
        );

        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 1L);
    }

    @Test
    @DisplayName("센서 데이터 등록")
    void register() {


        Mockito.when(sensorDataRepository.existsBySensor_SensorNoAndSensorDataName(1, sensorDataRegisterRequest.getSensorDataName()))
                .thenReturn(false);
        Mockito.when(sensorDataRepository.save(Mockito.any(SensorData.class))).thenReturn(sensorData);


        SensorDataResponse sensorDataResponse = sensorDataServiceImpl.register(sensor.getSensorNo(), sensorDataRegisterRequest);
        log.debug("dataTypeResponse:{}", sensorDataResponse);

        Mockito.verify(sensorDataRepository, Mockito.times(1)).save(Mockito.any(SensorData.class));
        Mockito.verify(sensorDataRepository, Mockito.times(1)).existsBySensor_SensorNoAndSensorDataName(Mockito.anyLong(), Mockito.anyString());

        Assertions.assertAll(
                () -> Assertions.assertNotNull(sensorDataResponse.getSensorDataNo()),
                () -> Assertions.assertEquals("입구", sensorDataResponse.getSensorDataLocation()),
                () -> Assertions.assertEquals("24e124fffef5ccc", sensorDataResponse.getSensorDataGateway()),
                () -> Assertions.assertEquals("lora", sensorDataResponse.getSensorDataName()),
                () -> Assertions.assertEquals(33.2, sensorDataResponse.getMinThreshold()),
                () -> Assertions.assertEquals(89.0, sensorDataResponse.getMaxThreshold())
        );
    }

    @Test
    @DisplayName("센서 데이터 등록 - 센서 데이터 이름 + 센서 번호로 중복 체크")
    void register_exception_case1() {
        Mockito.when(sensorDataRepository.existsBySensor_SensorNoAndSensorDataName(Mockito.anyLong(), Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(AlreadySensorDataExistException.class, () -> {
            sensorDataServiceImpl.register(sensor.getSensorNo(), sensorDataRegisterRequest);
        });

        Mockito.verify(sensorDataRepository, Mockito.times(1)).existsBySensor_SensorNoAndSensorDataName(Mockito.anyLong(), Mockito.anyString());
        Mockito.verify(sensorDataRepository, Mockito.never()).save(Mockito.any(SensorData.class));
    }


    @Test
    @DisplayName("센서 데이터 조회")
    void getSensorData() {

        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 1L);
        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(Optional.of(sensorData));

        SensorDataResponse sensorDataResponse = sensorDataServiceImpl.getSensorDataBySensorDataNo(sensorData.getSensorDataNo());

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());

        Assertions.assertAll(
                () -> Assertions.assertNotNull(sensorDataResponse),
                () -> Assertions.assertEquals(sensor.getSensorNo(), sensorDataResponse.getSensorNo()),
                () -> Assertions.assertEquals(1, sensorDataResponse.getSensorDataNo()),
                () -> Assertions.assertEquals("lora", sensorDataResponse.getSensorDataName()),
                () -> Assertions.assertEquals(33.2, sensorDataResponse.getMinThreshold()),
                () -> Assertions.assertEquals(89.0, sensorDataResponse.getMaxThreshold())
        );

    }

    @Test
    @DisplayName("센서 데이터 조회 - 센서 데이터 번호 중복 체크")
    void getSensorData_exception_case1() {

        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 1L);
        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(SensorDataNotExistException.class, () -> {
            sensorDataServiceImpl.getSensorDataBySensorDataNo(1);
        });

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());

    }

    @Test
    @DisplayName("센서 데이터 수정 - 성공 케이스")
    void update_success() {

        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong()))
                .thenReturn(Optional.of(sensorData));

        Mockito.when(sensorDataRepository.save(Mockito.any(SensorData.class)))
                .thenReturn(sensorData);

        SensorDataUpdateRequest sensorDataUpdateRequest = new SensorDataUpdateRequest(
                "입구", null, "battery",
                45.2, 77.0
        );

        SensorDataResponse sensorDataResponse = sensorDataServiceImpl.update(1, sensorDataUpdateRequest);
        log.debug("dataTypeResponse:{}", sensorDataResponse);

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());

        Assertions.assertAll(
                () -> Assertions.assertNotNull(sensorDataResponse.getSensorDataNo()),
                () -> Assertions.assertEquals(sensor.getSensorNo(), sensorDataResponse.getSensorNo()),
                () -> Assertions.assertEquals(1, sensorDataResponse.getSensorDataNo()),
                () -> Assertions.assertEquals("battery", sensorDataResponse.getSensorDataName()),
                () -> Assertions.assertEquals(45.2, sensorDataResponse.getMinThreshold()),
                () -> Assertions.assertEquals(77.0, sensorDataResponse.getMaxThreshold())
        );
    }


    @Test
    @DisplayName("센서 데이터 삭제")
    void delete() {

        Mockito.when(sensorDataRepository.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(Optional.of(sensorData));
        Mockito.doNothing().when(sensorDataRepository).delete(Mockito.any(SensorData.class));

        Void sensorDataResponse = sensorDataServiceImpl.delete(sensorData.getSensorDataNo());

        Mockito.verify(sensorDataRepository, Mockito.times(1)).getSensorDataBySensorDataNo(Mockito.anyLong());
        Mockito.verify(sensorDataRepository, Mockito.times(1)).delete(Mockito.any(SensorData.class));

        Assertions.assertNull(sensorDataResponse);
    }

}
