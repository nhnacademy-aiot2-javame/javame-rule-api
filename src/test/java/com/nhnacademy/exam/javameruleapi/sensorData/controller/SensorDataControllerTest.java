package com.nhnacademy.exam.javameruleapi.sensorData.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.service.SensorDataService;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class SensorDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    @MockitoBean
    private SensorDataService sensorDataService; // 서비스 계층을 모킹해서 주입

    private Sensor sensor;
    private SensorDataRegisterRequest sensorDataRegisterRequest;
    private SensorData sensorData;
    private SensorDataResponse sensorDataResponse;

    @Autowired
    private ServerService serverService;

    @BeforeEach
    void setUp(){
        sensor = new Sensor("test_domain", "main_sensor");
        sensorDataRegisterRequest = new SensorDataRegisterRequest
                (sensor, "test_dataName", 55.2, 99.9);
        sensorData = new SensorData(sensorDataRegisterRequest.getSensor(), sensorDataRegisterRequest.getSensorDataName(), sensorDataRegisterRequest.getMinThreshold(), sensorDataRegisterRequest.getMaxThreshold());
        ReflectionTestUtils.setField(sensorData, "dataTypeNo", 3L);


    }

    @Test
    @DisplayName("등록 테스트")
    void registerDataType() throws Exception {

        sensorDataResponse = new SensorDataResponse
                (3L, sensor, "test_dataName", 55.2, 99.9);

        Mockito.when(sensorDataService.register(Mockito.any(SensorDataRegisterRequest.class))).thenReturn(sensorDataResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(sensorDataRegisterRequest);

        mockMvc.perform(
                post("/sensor-datas")
                        .content(jsonRequest)
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensor.companyDomain").value("test_domain"))
                .andExpect(jsonPath("$.sensor.sensorId").value("main_sensor"))
                .andExpect(jsonPath("$.dataTypeName").value("test_dataName"))
                .andExpect(jsonPath("$.minThreshold").value(55.2))
                .andExpect(jsonPath("$.maxThreshold").value(99.9))
                .andDo(print());
    }

    @Test
    @DisplayName("데이터 타입 조회")
    void getDataType() throws Exception {

        sensorDataResponse = new SensorDataResponse(3L, sensor, sensorData.getSensorDataName(), sensorData.getMinThreshold(), sensorData.getMaxThreshold());

        Mockito.when(sensorDataService.getSensorData(Mockito.anyLong())).thenReturn(sensorDataResponse);

        mockMvc.perform(
                get("/sensor-datas/3")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataTypeNo").value(3))
                .andExpect(jsonPath("$.sensor.companyDomain").value("test_domain"))
                .andExpect(jsonPath("$.sensor.sensorId").value("main_sensor"))
                .andExpect(jsonPath("$.dataTypeName").value("test_dataName"))
                .andExpect(jsonPath("$.minThreshold").value(55.2))
                .andExpect(jsonPath("$.maxThreshold").value(99.9))
                .andDo(print());


    }

    @Test
    @DisplayName("데이터 타입 수정")
    void update() throws Exception{

        SensorDataUpdateRequest sensorDataUpdateRequest = new SensorDataUpdateRequest("real_dataName", 33.3, 56.2);

        sensorDataResponse = new SensorDataResponse(3L, sensor, "real_dataName", 33.3, 56.2 );

        Mockito.when(sensorDataService.update(Mockito.anyLong(), Mockito.any(SensorDataUpdateRequest.class))).thenReturn(sensorDataResponse);

        mockMvc.perform(
                put("/sensor-datas/3")
                        .content("""
                                {
                                "dataTypeName":"real_dataName",
                                "minThreshold":33.3,
                                "maxThreshold":56.2
                                }
                                """)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataTypeNo").value(3))
                .andExpect(jsonPath("$.sensor.companyDomain").value("test_domain"))
                .andExpect(jsonPath("$.dataTypeName").value("real_dataName"))
                .andExpect(jsonPath("$.minThreshold").value(33.3))
                .andExpect(jsonPath("$.maxThreshold").value(56.2))
                .andDo(print());
    }

    @Test
    @DisplayName("데이터 타입 삭제")
    void delete() throws Exception {

        Mockito.doNothing().when(sensorDataService).delete(Mockito.anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/sensor-datas/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent())
                .andDo(print());


    }





}
