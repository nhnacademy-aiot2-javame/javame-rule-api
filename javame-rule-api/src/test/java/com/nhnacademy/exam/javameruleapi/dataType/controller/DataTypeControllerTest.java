package com.nhnacademy.exam.javameruleapi.dataType.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeRegisterRequest;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeResponse;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeUpdateRequest;
import com.nhnacademy.exam.javameruleapi.dataType.service.DataTypeService;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(controllers = DataTypeController.class) //controller 만 bean으로 로드, 나머지는 직접 주입 필요.
public class DataTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    @MockitoBean
    private DataTypeService dataTypeService; // 서비스 계층을 모킹해서 주입

    private Sensor sensor;
    private DataTypeRegisterRequest dataTypeRegisterRequest;
    private DataTypeResponse dataTypeResponse;
    @Autowired
    private ServerService serverService;

    @BeforeEach
    void setUp(){
        sensor = new Sensor("test_domain", "main_sensor");
        dataTypeRegisterRequest = new DataTypeRegisterRequest
                (sensor, "test_dataName", 55.2, 99.9);

        dataTypeResponse = new DataTypeResponse
                (3L, sensor, "test_dataName", 55.2, 99.9);
    }

    @Test
    @DisplayName("등록 테스트")
    void registerDataType() throws Exception {


        Mockito.when(dataTypeService.register(Mockito.any(DataTypeRegisterRequest.class))).thenReturn(dataTypeResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(dataTypeRegisterRequest);

        mockMvc.perform(
                post("/data-types")
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

        Mockito.when(dataTypeService.getDataType(Mockito.anyLong())).thenReturn(dataTypeResponse);

        mockMvc.perform(
                get("/data-types/3")
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

        DataTypeUpdateRequest dataTypeUpdateRequest = new DataTypeUpdateRequest("real_dataName", 33.3, 56.2);

        dataTypeResponse = new DataTypeResponse(3L, sensor, "real_dataName", 33.3, 56.2 );

        Mockito.when(dataTypeService.update(Mockito.anyLong(), Mockito.any(DataTypeUpdateRequest.class))).thenReturn(dataTypeResponse);

        mockMvc.perform(
                put("/data-types/3")
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

        Mockito.doNothing().when(dataTypeService).delete(Mockito.anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/data-types/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent())
                .andDo(print());


    }





}
