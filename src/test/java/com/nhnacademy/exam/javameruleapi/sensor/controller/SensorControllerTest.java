//package com.nhnacademy.exam.javameruleapi.sensor.controller;
//
//import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
//import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
//import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class SensorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private SensorService sensorService; // Controller가 의존하는 Bean 만 Mock
//
//    private SensorRegisterRequest sensorRegisterRequest;
//    private Sensor sensor;
//    private SensorResponse sensorResponse;
//    private List<SensorDataRegisterRequest> sensorDataList;
//
//    @BeforeEach
//    void setUp() {
//        SensorDataRegisterRequest sensorDataRegisterRequest1 = new SensorDataRegisterRequest("temperature", null, null);
//        SensorDataRegisterRequest sensorDataRegisterRequest2 = new SensorDataRegisterRequest("humidity", null, null);
//        sensorDataList.add(sensorDataRegisterRequest1);
//        sensorDataList.add(sensorDataRegisterRequest2);
//
//        sensorRegisterRequest = new SensorRegisterRequest("nhn_domain", "mock_sensor_id", sensorDataList);
//        sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
//        ReflectionTestUtils.setField(sensor, "sensorNo", 1L);
//    }
//
//
//    @Test
//    @DisplayName("센서 등록")
//    void register() throws Exception {
//
//        List<SensorDataResponse> sensorDataResponses = sensor.getSensorDataList().stream()
//                .map(data -> new SensorDataResponse(
//                        data.getSensorDataNo(),
//                        data.getSensorDataName(),
//                        data.getMinThreshold(),
//                        data.getMaxThreshold()
//                ))
//                .toList();
//
//        SensorResponse sensorResponse = new SensorResponse(sensor.getSensorNo(), sensor.getCompanyDomain(), sensor.getSensorId(), sensorDataResponses);
//        Mockito.when(sensorService.register(Mockito.any(SensorRegisterRequest.class))).thenReturn(sensorResponse);
//
//        mockMvc.perform(
//                        post("/sensors")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .content("""
//
//                                        {
//                                "companyDomain":"nhn_domain",
//                                "sensorId":"mock_sensor_id"
//                                }
//                                """)
//                        )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.sensorNo").value(1))
//                .andExpect(jsonPath("$.companyDomain").value("nhn_domain"))
//                .andExpect(jsonPath("$.sensorId").value("mock_sensor_id"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("센서번호로 센서  조회")
//    void getSensor() throws Exception{
//
//        List<SensorDataResponse> sensorDataResponses = sensor.getSensorDataList().stream()
//                .map(data -> new SensorDataResponse(
//                        data.getSensorDataNo(),
//                        data.getSensorDataName(),
//                        data.getMinThreshold(),
//                        data.getMaxThreshold()
//                ))
//                .toList();
//        SensorResponse sensorResponse = new SensorResponse(sensor.getSensorNo(), sensor.getCompanyDomain(), sensor.getSensorId(), sensorDataResponses);
//
//        Mockito.when(sensorService.getSensor(Mockito.anyLong())).thenReturn(sensorResponse);
//
//        mockMvc.perform(
//                get("/sensors/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.sensorNo").value(1))
//                .andExpect(jsonPath("$.companyDomain").value("nhn_domain"))
//                .andExpect(jsonPath("$.sensorId").value("mock_sensor_id"))
//                .andDo(print());
//
//    }
//
//    @Test
//    @DisplayName("회사 도메인으로 등록된 센서 리스트 조회")
//    void getSensors() throws Exception {
//
//        List<SensorDataResponse> sensorDataResponses = sensor.getSensorDataList().stream()
//                .map(data -> new SensorDataResponse(
//                        data.getSensorDataNo(),
//                        data.getSensorDataName(),
//                        data.getMinThreshold(),
//                        data.getMaxThreshold()
//                ))
//                .toList();
//
//        List<SensorResponse> sensorResponses = new ArrayList<>();
//        SensorResponse sensorResponse = new SensorResponse(sensor.getSensorNo(), sensor.getCompanyDomain(), sensor.getSensorId(), sensorDataResponses);
//        SensorResponse sensorResponse2 = new SensorResponse(2L, "nhn_domain", "mock_sensor_id2");
//        sensorResponses.add(sensorResponse);
//        sensorResponses.add(sensorResponse2);
//
//        Mockito.when(sensorService.getSensors(Mockito.anyString())).thenReturn(sensorResponses);
//
//
//        mockMvc.perform(
//                get("/sensors?companyDomain=nhn_domain")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].sensorNo").value(1))
//                .andExpect(jsonPath("$[0].companyDomain").value("nhn_domain"))
//                .andExpect(jsonPath("$[0].sensorId").value("mock_sensor_id"))
//                .andExpect(jsonPath("$[1].sensorNo").value(2))
//                .andExpect(jsonPath("$[1].companyDomain").value("nhn_domain"))
//                .andExpect(jsonPath("$[1].sensorId").value("mock_sensor_id2"))
//                .andDo(print());
//
//}
//
//    @Test
//    @DisplayName("센서 삭제")
//    void delete() throws Exception{
//
//        Mockito.doNothing().when(sensorService).delete(Mockito.anyLong());
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete("/sensors/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
//
//
//
//
//
//}
