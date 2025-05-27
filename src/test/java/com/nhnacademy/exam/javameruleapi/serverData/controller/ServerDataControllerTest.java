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


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @MockitoBean
    private ServerService serverService;

    @MockitoBean
    private ServerDataService serverDataService;


    private ServerData serverData;
    private ServerDataRegisterRequest serverDataRegisterRequest;
    private Server server;

    @BeforeEach
    void setUp() {

        server = new Server(
                "192.168.32.5",
                "nhn_academy"
        );

        ReflectionTestUtils.setField(server, "serverNo", 1L);

        serverDataRegisterRequest = new ServerDataRegisterRequest(
                1, "power_meter",
                "modbus", "power_watts",
                20.0, 89.2
        );


        serverData = new ServerData(
                server,
                serverDataRegisterRequest.getServerDataLocation(),
                serverDataRegisterRequest.getServerDataGateway(),
                serverDataRegisterRequest.getServerDataName(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold()
        );

        server.addServerData(serverData);

        ReflectionTestUtils.setField(serverData, "serverDataNo", 1L);
        log.debug("serverData.getServerDataNo():{}", serverData.getServerDataNo());

    }


    @Test
    @DisplayName("서버 데이터 등록")
    void registerServerData() throws Exception {


        ServerDataResponse serverDataResponse = new ServerDataResponse(
                1,
                "nhn_academy",
                1,
                "power_meter",
                "modbus",
                "power_watts",
                20.0,
                89.2,
                LocalDateTime.now()
        );

        Mockito.when(serverDataService.registerServerData(Mockito.anyLong(), Mockito.any(ServerDataRegisterRequest.class)))
                .thenReturn(serverDataResponse);

        mockMvc.perform(
                        post("/server-datas?serverNo=1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-USER-ROLE", "ROLE_ADMIN")
                                .content("""
                                        {
                                        "serverNo":1,
                                        "companyDomain":"nhn_academy",
                                        "serverDataNo":1,                                                                                
                                        "serverDataLocation":"power_meter",
                                        "serverDataGateway":"modbus",
                                        "serverDataName":"power_watts",                                                                                 
                                        "minThreshold":20.0,
                                        "maxThreshold":89.2
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serverNo").value(1))
                .andExpect(jsonPath("$.companyDomain").value("nhn_academy"))
                .andExpect(jsonPath("$.serverDataNo").value(1))
                .andExpect(jsonPath("$.serverDataLocation").value("power_meter"))
                .andExpect(jsonPath("$.serverDataGateway").value("modbus"))
                .andExpect(jsonPath("$.serverDataName").value("power_watts"))
                .andExpect(jsonPath("$.minThreshold").value(20.0))
                .andExpect(jsonPath("$.maxThreshold").value(89.2))
                .andDo(print());


    }

    @Test
    @DisplayName("서버 데이터 조회")
    void getServerData() throws Exception {

        ServerDataResponse serverDataResponse = new ServerDataResponse(
                1,
                "nhn_academy",
                1,
                "power_meter",
                "modbus",
                "power_watts",
                20.0,
                89.2,
                LocalDateTime.now()
        );

        Mockito.when(serverDataService.getServerData(Mockito.anyLong())).thenReturn(serverDataResponse);

        mockMvc.perform(
                        get("/server-datas/by-server-data-no/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-USER-ROLE", "ROLE_ADMIN")


                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.serverNo").value(1))
                .andExpect(jsonPath("$.companyDomain").value("nhn_academy"))
                .andExpect(jsonPath("$.serverDataNo").value(1))
                .andExpect(jsonPath("$.serverDataLocation").value("power_meter"))
                .andExpect(jsonPath("$.serverDataGateway").value("modbus"))
                .andExpect(jsonPath("$.serverDataName").value("power_watts"))
                .andExpect(jsonPath("$.minThreshold").value(20.0))
                .andExpect(jsonPath("$.maxThreshold").value(89.2))
                .andDo(print());
    }


    @Test
    @DisplayName("서버 데이터 리스트 조회 - 서버 번호")
    void getServerDataList() throws Exception {

        ServerDataRegisterRequest serverDataRegisterRequest1 = new ServerDataRegisterRequest(
                1,
                "server_resource_data",
                "modbus",
                "power_watts",
                34.2,
                98.0
        );

        ServerData serverData1 = new ServerData(
                server,
                serverDataRegisterRequest1.getServerDataLocation(),
                serverDataRegisterRequest1.getServerDataGateway(),
                serverDataRegisterRequest1.getServerDataName(),
                serverDataRegisterRequest1.getMinThreshold(),
                serverDataRegisterRequest1.getMaxThreshold()

        );

        server.addServerData(serverData1);
        ReflectionTestUtils.setField(serverData, "serverDataNo", 2);

        ServerDataResponse serverDataResponse1 = new ServerDataResponse(
                1,
                "nhn_academy",
                1,
                "power_meter",
                "modbus",
                "power_watts",
                20.0,
                89.2,
                LocalDateTime.now()
        );

        ServerDataResponse serverDataResponse2 = new ServerDataResponse(
                1,
                "nhn_academy",
                2,
                "server_resource_data",
                "modbus",
                "power_watts",
                34.2,
                98.0,
                LocalDateTime.now()
        );

        List<ServerDataResponse> serverDataResponseList = new ArrayList<>();
        serverDataResponseList.add(serverDataResponse1);
        serverDataResponseList.add(serverDataResponse2);

        Mockito.when(serverDataService.getServerDataList(Mockito.anyLong())).thenReturn(serverDataResponseList);

        mockMvc.perform(
                        get("/server-datas/by-server-no/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-USER-ROLE", "ROLE_ADMIN", "ROLE_USER")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serverNo").value(1))
                .andExpect(jsonPath("$[1].companyDomain").value("nhn_academy"))
                .andExpect(jsonPath("$[0].serverDataNo").value(1))
                .andExpect(jsonPath("$[1].serverDataLocation").value("server_resource_data"))
                .andExpect(jsonPath("$[0].serverDataGateway").value("modbus"))
                .andExpect(jsonPath("$[1].serverDataName").value("power_watts"))
                .andExpect(jsonPath("$[0].minThreshold").value(20.0))
                .andExpect(jsonPath("$[1].maxThreshold").value(98.0))
                .andDo(print());

    }


    @Test
    @DisplayName("서버 데이터 업데이트")
    void updateServerData() throws Exception {

        ServerDataUpdateRequest serverDataUpdateRequest = new ServerDataUpdateRequest(
                "power_meter",
                "modbus",
                "current_amps",
                45.2,
                87.8
        );


        ServerDataResponse serverDataResponse = new ServerDataResponse(
                server.getServerNo(),
                server.getCompanyDomain(),
                serverData.getServerDataNo(),
                serverDataUpdateRequest.getServerDataLocation(),
                serverDataUpdateRequest.getServerDataGateway(),
                serverDataUpdateRequest.getServerDataName(),
                45.2,
                87.8,
                LocalDateTime.now()
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
                                                "serverDataLocation":"power_meter",
                                                "serverDataGateway":"modbus",
                                                "serverDataName":"current_amps",
                                                "minThreshold": 45.2,
                                                "maxThreshold": 87.8
                                                }
                                                """
                                )
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.serverDataNo").value(1))
                .andExpect(jsonPath("$.serverDataLocation").value("power_meter"))
                .andExpect(jsonPath("$.serverDataGateway").value("modbus"))
                .andExpect(jsonPath("$.serverDataName").value("current_amps"))
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
