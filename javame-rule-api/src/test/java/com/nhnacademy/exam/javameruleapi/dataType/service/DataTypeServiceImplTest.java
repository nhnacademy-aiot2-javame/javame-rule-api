package com.nhnacademy.exam.javameruleapi.dataType.service;

import com.nhnacademy.exam.javameruleapi.dataType.common.Exception.AlreadyDataTypeExistException;
import com.nhnacademy.exam.javameruleapi.dataType.common.Exception.ConflictException;
import com.nhnacademy.exam.javameruleapi.dataType.common.Exception.DataTypeNotExistException;
import com.nhnacademy.exam.javameruleapi.dataType.domain.DataType;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeRegisterRequest;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeResponse;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeUpdateRequest;
import com.nhnacademy.exam.javameruleapi.dataType.repository.DataTypeRepository;
import com.nhnacademy.exam.javameruleapi.dataType.service.Impl.DataTypeServiceImpl;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DataTypeServiceImplTest {


    @Mock
    DataTypeRepository dataTypeRepository;


    @InjectMocks
    DataTypeServiceImpl dataTypeService;

    private SensorRegisterRequest sensorRegisterRequest;
    private Sensor sensor;
    private DataTypeRegisterRequest dataTypeRegisterRequest;
    private DataType dataType;


    @BeforeEach
    void setUp(){
        sensorRegisterRequest = new SensorRegisterRequest("nhn_company", "123456789");

        sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
        // DB에 저장된게 아니라서 sensorNo가 null일 수 있음.
        ReflectionTestUtils.setField(sensor, "sensorNo", 1L);

        dataTypeRegisterRequest = new DataTypeRegisterRequest(sensor, "temperature", 33.2, 89.0);

        dataType = new DataType(
                dataTypeRegisterRequest.getSensor(),
                dataTypeRegisterRequest.getDataTypeName(),
                dataTypeRegisterRequest.getMinThreshold(),
                dataTypeRegisterRequest.getMaxThreshold()
        );

//        DataTypeResponse dataTypeResponse = dataTypeService.register(dataTypeRegisterRequest);

        ReflectionTestUtils.setField(dataType, "dataTypeNo", 1L);
    }

    @Test
    @DisplayName("데이터 타입 등록")
    void register() {


        Mockito.when(dataTypeRepository.existsDataTypeByDataTypeName(dataTypeRegisterRequest.getDataTypeName())).thenReturn(false);
        Mockito.when(dataTypeRepository.save(Mockito.any(DataType.class))).thenReturn(dataType);


        DataTypeResponse dataTypeResponse = dataTypeService.register(dataTypeRegisterRequest); // 데이터 타입 등록.
        log.debug("dataTypeResponse:{}", dataTypeResponse);

        Mockito.verify(dataTypeRepository, Mockito.times(1)).save(Mockito.any(DataType.class));
        Mockito.verify(dataTypeRepository, Mockito.times(1)).existsDataTypeByDataTypeName((Mockito.anyString()));

        Assertions.assertAll(
                ()-> Assertions.assertNotNull(dataTypeResponse.getDataTypeNo()),
                ()-> Assertions.assertEquals("temperature", dataTypeResponse.getDataTypeName()),
                ()-> Assertions.assertEquals(33.2, dataTypeResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(89.0, dataTypeResponse.getMaxThreshold())
        );
    }

    @Test
    @DisplayName("데이터 타입 등록 - 데이터 타입 이름 중복 체크")
    void register_exception_case1(){
        Mockito.when(dataTypeRepository.existsDataTypeByDataTypeName(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(AlreadyDataTypeExistException.class, ()-> {
            dataTypeService.register(dataTypeRegisterRequest);
        });

        Mockito.verify(dataTypeRepository, Mockito.times(1)).existsDataTypeByDataTypeName(Mockito.anyString());
        Mockito.verify(dataTypeRepository, Mockito.never()).save(Mockito.any(DataType.class));
    }


    @Test
    @DisplayName("데이터 타입 조회")
    void getDataType() {

        ReflectionTestUtils.setField(dataType, "dataTypeNo", 1L);
        Mockito.when(dataTypeRepository.getDataTypeByDataTypeNo(Mockito.anyLong())).thenReturn(Optional.of(dataType));

        DataTypeResponse dataTypeResponse = dataTypeService.getDataType(dataType.getDataTypeNo());

        Mockito.verify(dataTypeRepository, Mockito.times(1)).getDataTypeByDataTypeNo(Mockito.anyLong());

        Assertions.assertAll(
                ()-> Assertions.assertNotNull(dataTypeResponse),
                ()-> Assertions.assertEquals(sensor.getSensorNo(), dataTypeResponse.getSensor().getSensorNo()),
                ()-> Assertions.assertEquals(1, dataTypeResponse.getDataTypeNo()),
                ()-> Assertions.assertEquals("temperature", dataTypeResponse.getDataTypeName()),
                ()-> Assertions.assertEquals(33.2, dataTypeResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(89.0, dataTypeResponse.getMaxThreshold())
        );

    }

    @Test
    @DisplayName("데이터 타입 조회 - 데이터 타입 번호 중복 체크")
    void getDataType_exception_case1(){

        ReflectionTestUtils.setField(dataType, "dataTypeNo", 1L);
        Mockito.when(dataTypeRepository.getDataTypeByDataTypeNo(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(DataTypeNotExistException.class, () -> {
            dataTypeService.getDataType(1);
        });

        Mockito.verify(dataTypeRepository, Mockito.times(1)).getDataTypeByDataTypeNo(Mockito.anyLong());

    }

    @Test
    @DisplayName("데이터 타입 수정")
    void update(){

        Mockito.when(dataTypeRepository.getDataTypeByDataTypeNo(Mockito.anyLong())).thenReturn(Optional.of(dataType));
        Mockito.when(dataTypeRepository.save(Mockito.any(DataType.class))).thenReturn(dataType);

        DataTypeUpdateRequest dataTypeUpdateRequest = new DataTypeUpdateRequest("humidity", 33.2, 90.4);

        DataTypeResponse dataTypeResponse = dataTypeService.update(1, dataTypeUpdateRequest);
        log.debug("dataTypeResponse:{}", dataTypeResponse);

        Mockito.verify(dataTypeRepository, Mockito.times(1)).getDataTypeByDataTypeNo(Mockito.anyLong());

        Assertions.assertAll(
                ()-> Assertions.assertNotNull(dataTypeResponse.getDataTypeNo()),
                ()-> Assertions.assertEquals(sensor.getSensorNo(), dataTypeResponse.getSensor().getSensorNo()),
                ()-> Assertions.assertEquals(1, dataTypeResponse.getDataTypeNo()),
                ()-> Assertions.assertEquals("humidity", dataTypeResponse.getDataTypeName()),
                ()-> Assertions.assertEquals(33.2, dataTypeResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(90.4, dataTypeResponse.getMaxThreshold())
        );
    }


    @Test
    @DisplayName("데이터 타입 삭제")
    void delete() {

        Mockito.when(dataTypeRepository.getDataTypeByDataTypeNo(Mockito.anyLong())).thenReturn(Optional.of(dataType));
        Mockito.doNothing().when(dataTypeRepository).delete(Mockito.any(DataType.class));

        Void dataTypeResponse = dataTypeService.delete(dataType.getDataTypeNo());

        Mockito.verify(dataTypeRepository, Mockito.times(1)).getDataTypeByDataTypeNo(Mockito.anyLong());
        Mockito.verify(dataTypeRepository, Mockito.times(1)).delete(Mockito.any(DataType.class));

        Assertions.assertNull(dataTypeResponse);
    }

}
