package com.nhnacademy.exam.javameruleapi.sensorData.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.service.SensorDataService;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;

import jakarta.transaction.Transactional;
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


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class SensorDataControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private SensorDataService sensorDataService; // 서비스 계층을 모킹해서 주입

    private Sensor sensor;
    private SensorDataRegisterRequest sensorDataRegisterRequest;
    private SensorData sensorData;
    private SensorDataResponse sensorDataResponse;
    private List<SensorDataResponse> sensorDataResponses = new ArrayList<>();




    @BeforeEach
    void setUp() {
        sensor = new Sensor("test_domain", "12345e");
        ReflectionTestUtils.setField(sensor, "sensorNo", 1L);
        sensorDataRegisterRequest = new SensorDataRegisterRequest
                ("입구", "24e124fffef5ccc",
                        "sdfsgh", 59.9, 89.9);
        sensorData = new SensorData(
                sensor,
                sensorDataRegisterRequest.getSensorDataGateway(),
                sensorDataRegisterRequest.getSensorDataLocation(),
                sensorDataRegisterRequest.getSensorDataName(),
                sensorDataRegisterRequest.getMinThreshold(),
                sensorDataRegisterRequest.getMaxThreshold()
        );
        ReflectionTestUtils.setField(sensorData, "sensorDataNo", 3L);

        sensor.addSensorData(sensorData);
    }

    @Test
    @DisplayName("등록 테스트")
    void registerDataType() throws Exception {

        sensorDataResponse = new SensorDataResponse(
                sensor.getSensorNo(), sensor.getCompanyDomain(),
                sensorData.getSensorDataNo(), sensorData.getSensorDataLocation(),
                sensorData.getSensorDataGateway(), sensorData.getSensorDataName(),
                sensorData.getMinThreshold(), sensorData.getMaxThreshold(), sensorData.getCreatedAt()
        );

        Mockito.when(sensorDataService.register(Mockito.anyLong(), Mockito.any(SensorDataRegisterRequest.class))).thenReturn(sensorDataResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(sensorDataRegisterRequest);

        mockMvc.perform(
                        post("/rule/sensor-datas/1")
                                .header("X-USER-ROLE", "ROLE_ADMIN")
                                .content(jsonRequest)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyDomain").value("test_domain"))
                .andExpect(jsonPath("$.sensorDataLocation").value("입구"))
                .andExpect(jsonPath("$.sensorDataGateway").value("24e124fffef5ccc"))
                .andExpect(jsonPath("$.sensorDataName").value("sdfsgh"))
                .andExpect(jsonPath("$.minThreshold").value(59.9))
                .andExpect(jsonPath("$.maxThreshold").value(89.9))
                .andDo(print());
    }

    @Test
    @DisplayName("센서 데이터 번호로 센서 데이터 조회")
    void getSensorDataBySensorDataNo() throws Exception {

        sensorDataResponse = new SensorDataResponse
                (
                        sensor.getSensorNo(), sensor.getCompanyDomain(),
                        sensorData.getSensorDataNo(), sensorData.getSensorDataLocation(),
                        sensorData.getSensorDataGateway(), sensorData.getSensorDataName(),
                        sensorData.getMinThreshold(), sensorData.getMaxThreshold(), sensorData.getCreatedAt()
                );

        Mockito.when(sensorDataService.getSensorDataBySensorDataNo(Mockito.anyLong())).thenReturn(sensorDataResponse);

        mockMvc.perform(
                        get("/rule/sensor-datas/by-dt-no/3")
                                .header("X-USER-ROLE", "ROLE_ADMIN")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorDataNo").value(3))
                .andExpect(jsonPath("$.companyDomain").value("test_domain"))
                .andExpect(jsonPath("$.sensorDataLocation").value("입구"))
                .andExpect(jsonPath("$.sensorDataGateway").value("24e124fffef5ccc"))
                .andExpect(jsonPath("$.sensorDataName").value("sdfsgh"))
                .andExpect(jsonPath("$.minThreshold").value(59.9))
                .andExpect(jsonPath("$.maxThreshold").value(89.9))
                .andDo(print());

    }

    @Test
    @DisplayName("센서 번호로 센서 데이터 리스트 조회")
    void getSensorDataBySensorNo() throws Exception {

        sensorDataResponse = new SensorDataResponse
                (
                        sensor.getSensorNo(), sensor.getCompanyDomain(),
                        sensorData.getSensorDataNo(), sensorData.getSensorDataLocation(),
                        sensorData.getSensorDataGateway(), sensorData.getSensorDataName(),
                        sensorData.getMinThreshold(), sensorData.getMaxThreshold(), sensorData.getCreatedAt()
                );

        sensorDataResponses.add(sensorDataResponse);

        Mockito.when(sensorDataService.getSensorDatasBySensorNo(Mockito.anyLong())).thenReturn(sensorDataResponses);

        mockMvc.perform(
                        get("/rule/sensor-datas/by-sensor-no/1")
                                .header("X-USER-ROLE", "ROLE_ADMIN")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sensorDataNo").value(3))
                .andExpect(jsonPath("$[0].companyDomain").value("test_domain"))
                .andExpect(jsonPath("$[0].sensorDataLocation").value("입구"))
                .andExpect(jsonPath("$[0].sensorDataGateway").value("24e124fffef5ccc"))
                .andExpect(jsonPath("$[0].sensorDataName").value("sdfsgh"))
                .andExpect(jsonPath("$[0].minThreshold").value(59.9))
                .andExpect(jsonPath("$[0].maxThreshold").value(89.9))
                .andDo(print());
    }


    @Test
    @DisplayName("데이터 타입 수정")
    void update() throws Exception {


        sensorDataResponse = new SensorDataResponse
                (sensor.getSensorNo(), "test_domain2",
                        8, "출구",
                        "24e1340jnnnnn", "jknl",
                        56.3, 88.3,
                        sensor.getCreatedAt());

        Mockito.when(sensorDataService.update(Mockito.anyLong(), Mockito.any(SensorDataUpdateRequest.class))).thenReturn(sensorDataResponse);

        mockMvc.perform(
                        put("/rule/sensor-datas/3")
                                .content("""
                                        {
                                        "sensorDataLocation":"출구",
                                        "sensorDataGateway":"24e1340jnnnnn",
                                        "sensorDataName":"jknl",
                                        "minThreshold":56.3,
                                        "maxThreshold":88.3
                                        }
                                        """)
                                .header("X-USER-ROLE", "ROLE_ADMIN")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorDataNo").value(8))
                .andExpect(jsonPath("$.companyDomain").value("test_domain2"))
                .andExpect(jsonPath("$.sensorDataLocation").value("출구"))
                .andExpect(jsonPath("$.sensorDataGateway").value("24e1340jnnnnn"))
                .andExpect(jsonPath("$.sensorDataName").value("jknl"))
                .andExpect(jsonPath("$.minThreshold").value(56.3))
                .andExpect(jsonPath("$.maxThreshold").value(88.3))
                .andDo(print());
    }

    @Test
    @DisplayName("데이터 타입 삭제")
    void delete() throws Exception {

        Mockito.doNothing().when(sensorDataService).delete(Mockito.anyLong());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rule/sensor-datas/3")
                                .header("X-USER-ROLE", "ROLE_ADMIN")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print());


    }


}
