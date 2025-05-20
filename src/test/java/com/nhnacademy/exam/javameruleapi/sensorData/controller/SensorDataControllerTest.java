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
        sensor = new Sensor("test_domain", "12345e");
        sensorDataRegisterRequest = new SensorDataRegisterRequest
                ("12345e", "test_dataName", 55.2, 99.9, "nhn_academy");
        sensorData = new SensorData(
                sensorDataRegisterRequest.getSensorId(),
                sensorDataRegisterRequest.getSensorDataName(),
                sensorDataRegisterRequest.getMinThreshold(),
                sensorDataRegisterRequest.getMaxThreshold(),
                sensorDataRegisterRequest.getCompanyDomain()
        );
        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 3L);


    }

    @Test
    @DisplayName("등록 테스트")
    void registerDataType() throws Exception {

        sensorDataResponse = new SensorDataResponse
                ("12345e", 3L, "test_dataName", 55.2, 99.9, "nhn_academy");

        Mockito.when(sensorDataService.register(Mockito.anyString(), Mockito.any(SensorDataRegisterRequest.class))).thenReturn(sensorDataResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(sensorDataRegisterRequest);

        mockMvc.perform(
                post("/sensor-datas/12345e")
                        .header("X-USER-ROLE", "ROLE_ADMIN")
                        .content(jsonRequest)
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyDomain").value("nhn_academy"))
                .andExpect(jsonPath("$.sensorId").value("12345e"))
                .andExpect(jsonPath("$.sensorDataName").value("test_dataName"))
                .andExpect(jsonPath("$.minThreshold").value(55.2))
                .andExpect(jsonPath("$.maxThreshold").value(99.9))
                .andDo(print());
    }

    @Test
    @DisplayName("센서 데이터 번호로 센서 데이터 조회")
    void getSensorDataBySensorDataNo() throws Exception {

        sensorDataResponse = new SensorDataResponse
                (sensor.getSensorId(),3L, sensorData.getSensorDataName(),
                        sensorData.getMinThreshold(), sensorData.getMaxThreshold(), sensorData.getCompanyDomain());

        Mockito.when(sensorDataService.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(sensorDataResponse);

        mockMvc.perform(
                get("/sensor-datas/by-no/3")
                        .header("X-USER-ROLE", "ROLE_ADMIN")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorDataNo").value(3))
                .andExpect(jsonPath("$.companyDomain").value("nhn_academy"))
                .andExpect(jsonPath("$.sensorId").value("12345e"))
                .andExpect(jsonPath("$.sensorDataName").value("test_dataName"))
                .andExpect(jsonPath("$.minThreshold").value(55.2))
                .andExpect(jsonPath("$.maxThreshold").value(99.9))
                .andDo(print());

    }

    @Test
    @DisplayName("센서 아이디로 센서 데이터 조회")
    void getSensorDataBySensorId() throws Exception {

        sensorDataResponse = new SensorDataResponse
                (sensor.getSensorId(),3L, sensorData.getSensorDataName(),
                        sensorData.getMinThreshold(), sensorData.getMaxThreshold(), sensorData.getCompanyDomain());

        Mockito.when(sensorDataService.getSensorDataBySensorId(Mockito.anyString())).thenReturn(sensorDataResponse);

        mockMvc.perform(
                get("/sensor-datas/by-id/12345e")
                        .header("X-USER-ROLE", "ROLE_ADMIN")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorDataNo").value(3))
                .andExpect(jsonPath("$.companyDomain").value("nhn_academy"))
                .andExpect(jsonPath("$.sensorId").value("12345e"))
                .andExpect(jsonPath("$.sensorDataName").value("test_dataName"))
                .andExpect(jsonPath("$.minThreshold").value(55.2))
                .andExpect(jsonPath("$.maxThreshold").value(99.9))
                .andDo(print());
    }


    @Test
    @DisplayName("데이터 타입 수정")
    void update() throws Exception{


        sensorDataResponse = new SensorDataResponse(sensor.getSensorId(), 3L, "real_dataName", 33.3, 56.2, "nhn_academy" );

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
                        .header("X-USER-ROLE", "ROLE_ADMIN")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorDataNo").value(3))
                .andExpect(jsonPath("$.companyDomain").value("nhn_academy"))
                .andExpect(jsonPath("$.sensorDataName").value("real_dataName"))
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
                        .header("X-USER-ROLE", "ROLE_ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent())
                .andDo(print());


    }





}
