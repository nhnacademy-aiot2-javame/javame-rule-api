package com.nhnacademy.exam.javameruleapi.server.controller;


import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ServerController.class) // 컨트롤러 테스트 전용 어노테이션.
@AutoConfigureMockMvc
public class ServerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServerService serverService; //Controller가 의존하는 Bean만 Mock

    @Test
    @DisplayName("서버등록 테스트")
    void testRegisterServer() throws Exception {
        ServerRegisterRequest request = new ServerRegisterRequest(
                "192.168.0.1",
                "javaMe.com",
                "main_server",
                null, 75.0,
                null, null,
                22.3, 52.4
        );

        ServerResponse serverResponse = new ServerResponse(
                1L, "main_server",
                null, 75.0,
                null, null,
                22.3, 52.4,
                "javaMe.com"
        );

        Mockito.when(serverService.register(Mockito.any())).thenReturn(serverResponse);

        mockMvc.perform(
                        post("/servers")
                                .content("""
                                        {
                                        "serverNo": 1,
                                        "serverId": "main_server",
                                        "cpuUsageThreshold": null,
                                        "cpuTemperatureThreshold" : 75.0,
                                        "memoryUsageThreshold": null,
                                        "memoryTemperatureThreshold": null,
                                        "diskUsageThreshold":22.3,
                                        "diskTemperatureThreshold":52.4,
                                        "companyDomain":"javaMe.com" 
                                        }
                                        """)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.iphost").value("192.168.0.1")) //.andExpect(...)들은 응답으로 돌아온 JSON 결과나 HTTP 상태코드를 검증
                .andExpect(jsonPath("$.companyDomain").value("javaMe.com"))
                .andExpect(jsonPath("$.serverNo").value(1))
                .andExpect(jsonPath("$.cpuUsageThreshold").value(80.0))
                .andExpect(jsonPath("$.cpuTemperatureThreshold").value(75.9))
                .andExpect(jsonPath("$.memoryUsageThreshold").value(null))
                .andExpect(jsonPath("$.memoryTemperatureThreshold").value(null))
                .andExpect(jsonPath("$.diskUsageThreshold").value(22.3))
                .andExpect(jsonPath("$.diskTemperatureThreshold").value(52.4))
                .andDo(print());
    }
}