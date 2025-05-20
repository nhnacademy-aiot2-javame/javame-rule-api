package com.nhnacademy.exam.javameruleapi.serverData.controller;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.serverData.service.ServerDataService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ServerDataControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServerService serverService;

    @MockitoBean
    private ServerDataService serverDataService;



    private ServerData serverData;
    private ServerDataRegisterRequest serverDataRegisterRequest;

    @BeforeEach
    void setUp(){

        serverDataRegisterRequest = new ServerDataRegisterRequest(
                "192.168.32.5", "Mail Server",
                "Network", 20.0,
                80.0, "nhn_academy"
        );

        serverData = new ServerData(
                serverDataRegisterRequest.getIphost(),
                serverDataRegisterRequest.getServerDataCategory(),
                serverDataRegisterRequest.getServerDataTopic(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold(),
                serverDataRegisterRequest.getCompanyDomain()
        );

        ReflectionTestUtils.setField(serverData, "serverDataNo", 1L);
        log.debug("serverData.getServerDataNo():{}", serverData.getServerDataNo());

    }


    @Test
    @DisplayName("서버 데이터 등록")
    void registerServerData() throws Exception {


        ServerDataResponse serverDataResponse = new ServerDataResponse(
                1L,
                "192.168.32.5",
                "Mail Server",
                "Network",
                20.0,
                80.0
        );

        Mockito.when(serverDataService.registerServerData(Mockito.any(ServerDataRegisterRequest.class))).thenReturn(serverDataResponse);

        mockMvc.perform(
                        post("/server-datas")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-USER-ROLE", "ROLE_ADMIN")
                                .content("""
                                        {
                                        "iphost":"192.168.32.5",
                                        "serverDataCategory":"Mail Server",
                                        "serverDataTopic":"Network",
                                        "minThreshold":20.0,
                                        "maxThreshold":80.0
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serverDataNo").value(1))
                .andExpect(jsonPath("$.iphost").value("192.168.32.5"))
                .andExpect(jsonPath("$.serverDataCategory").value("Mail Server"))
                .andExpect(jsonPath("$.serverDataTopic").value("Network"))
                .andExpect(jsonPath("$.minThreshold").value(20.0))
                .andExpect(jsonPath("$.maxThreshold").value(80.0))
                .andDo(print());


    }

    @Test
    @DisplayName("서버 데이터 조회")
    void getServerData() throws Exception {

        ServerDataResponse serverDataResponse = new ServerDataResponse(
                1L,
                "192.168.32.5",
                "Mail Server",
                "Network",
                20.0,
                80.0
        );

        Mockito.when(serverDataService.getServerData(Mockito.anyLong())).thenReturn(serverDataResponse);

        mockMvc.perform(
                get("/server-datas/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ROLE", "ROLE_ADMIN")


        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.serverDataNo").value(1))
                .andExpect(jsonPath("$.iphost").value("192.168.32.5"))
                .andExpect(jsonPath("$.serverDataCategory").value("Mail Server"))
                .andExpect(jsonPath("$.serverDataTopic").value("Network"))
                .andExpect(jsonPath("$.minThreshold").value(20.0))
                .andExpect(jsonPath("$.maxThreshold").value(80.0))
                .andDo(print());
    }



    @Test
    @DisplayName("서버 데이터 업데이트")
    void updateServerData() throws Exception{

        ServerDataUpdateRequest serverDataUpdateRequest = new ServerDataUpdateRequest(
                "Data Server",
                "Usage",
                45.2,
                87.8
        );


        ServerDataResponse serverDataResponse = new ServerDataResponse(
                1L,
                "192.168.32.5",
                "Data Server",
                "Usage",
                45.2,
                87.8
        );

        Mockito.when(serverDataService.updateServerData(Mockito.anyLong(), Mockito.any(ServerDataUpdateRequest.class))).thenReturn(serverDataResponse);

        mockMvc.perform(
                put("/server-datas/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-USER-ROLE", "ROLE_ADMIN")
                        .content(
                                """
                                {
                                "serverDataCategory":"Data Server",
                                "serverDataTopic":"Usage",
                                "minThreshold": 45.2,
                                "maxThreshold": 87.8
                                }
                                """
                        )
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.serverDataNo").value(1))
                .andExpect(jsonPath("$.iphost").value("192.168.32.5"))
                .andExpect(jsonPath("$.serverDataCategory").value("Data Server"))
                .andExpect(jsonPath("$.serverDataTopic").value("Usage"))
                .andExpect(jsonPath("$.minThreshold").value(45.2))
                .andExpect(jsonPath("$.maxThreshold").value(87.8))
                .andDo(print());
    }



    @Test
    @DisplayName("서버 데이터 삭제")
    void deleteServerData() throws Exception {

        Mockito.doNothing().when(serverDataService).delete(Mockito.anyLong());

        mockMvc.perform(
                delete("/server-datas/1")
                        .header("X-USER-ROLE", "ROLE_ADMIN")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent())
                .andDo(print());

    }
}
