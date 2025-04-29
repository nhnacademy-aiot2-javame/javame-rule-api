package com.nhnacademy.exam.javameruleapi.server.controller;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DisplayName("서버등록")
    void registerServer() throws Exception {

        ServerRegisterRequest serverRegisterRequest = new ServerRegisterRequest("192.168.0.1", "javaMe.com");

        ServerResponse serverResponse = new ServerResponse(
                1L, "javaMe.com",
                "192.168.0.1"
        );

        Mockito.when(serverService.register(Mockito.any(ServerRegisterRequest.class))).thenReturn(serverResponse);

        mockMvc.perform(
                        post("/servers")
                                .content("""
                                        {
                                        "companyDomain":"javaMe.com",
                                        "iphost" : "192.168.0.1"
                                        }
                                        """)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serverNo").value(1))
                .andExpect(jsonPath("$.companyDomain").value("javaMe.com"))
                .andExpect(jsonPath("$.iphost").value("192.168.0.1"))
                .andDo(print());
    }

    @Test
    @DisplayName("서버 번호로 조회")
    void getServer() throws Exception {

        ServerResponse serverResponse = new ServerResponse(
                1L, "javaMe.com",
                "192.168.0.1"
        );

        Mockito.when(serverService.getServer(Mockito.anyLong())).thenReturn(serverResponse);

        mockMvc.perform(
                get("/servers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serverNo").value(1))
                .andExpect(jsonPath("$.companyDomain").value("javaMe.com"))
                .andExpect(jsonPath("$.iphost").value("192.168.0.1"))
                .andDo(print());
    }




    @Test
    @DisplayName("회사 도메인으로 서버 리스트 조회")
    void getServersByCompanyDomain() throws Exception{

        List<ServerResponse> serverResponses = new ArrayList<>();
        ServerResponse rsp1 = new ServerResponse(
                1L, "javaMe.com",
                "192.168.0.1"
        );
        ServerResponse rsp2 = new ServerResponse(
                2L, "javaMe.com",
                "210.33.4"
        );
        serverResponses.add(rsp1);
        serverResponses.add(rsp2);

        Mockito.when(serverService.getServers(Mockito.anyString())).thenReturn(serverResponses);

        mockMvc.perform(
                get("/servers?companyDomain=javaMe.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serverNo").value(1))
                .andExpect(jsonPath("$[0].companyDomain").value("javaMe.com"))
                .andExpect(jsonPath("$[0].iphost").value("192.168.0.1"))
                .andExpect(jsonPath("$[1].serverNo").value(2))
                .andExpect(jsonPath("$[1].companyDomain").value("javaMe.com"))
                .andExpect(jsonPath("$[1].iphost").value("210.33.4"))
                .andDo(print());

        }




        @Test
        @DisplayName("서버 수정")
        void update() throws Exception{



            ServerResponse serverResponse = new ServerResponse(1L, "javaMe.com",
                    "200.31.2"
            );


            ServerUpdateRequest serverUpdateRequest = new ServerUpdateRequest("200.31.2");

            Mockito.when(serverService.update(Mockito.anyLong(),Mockito.any(ServerUpdateRequest.class))).thenReturn(serverResponse);

            mockMvc.perform(
                    put("/servers/1")
                            .content("""
                                    {
                                    "iphost":"200.31.2"
                                        }
                                    """)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)

            ).andExpect(status().isOk())
                    .andExpect(jsonPath("$.serverNo").value(1))
                    .andExpect(jsonPath("$.companyDomain").value("javaMe.com"))
                    .andExpect(jsonPath("$.iphost").value("200.31.2"))
                    .andDo(print());

    }

    @Test
    @DisplayName("서버 삭제")
    void delete() throws Exception {

        Mockito.doNothing().when(serverService).delete(Mockito.anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/servers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent())
                .andDo(print());


    }


}