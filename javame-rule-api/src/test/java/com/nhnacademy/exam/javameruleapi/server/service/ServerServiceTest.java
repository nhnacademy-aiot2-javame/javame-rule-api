//package com.nhnacademy.exam.javameruleapi.server.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nhnacademy.exam.javameruleapi.server.domain.Server;
//import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
//import com.nhnacademy.exam.javameruleapi.server.service.Impl.ServerServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Optional;
//
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class ServerServiceTest {
//
//
//    @Mock
//    ServerRepository serverRepository; //가짜 DB 역할.
//
//    @InjectMocks //내가 테스트할 대상 클래스에 붙임.
//    ServerServiceImpl serverServiceImpl; //테스트 할 진짜 서비스 객체, mock이 주입됨.
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @DisplayName("서버 등록 테스트")
//    void testRegisterServer() throws Exception {
//        // 가짜 request 데이터 생성
//        ServerRegisterRequest request = new ServerRegisterRequest(
//                "192.168.0.1",
//                "javaMe.com",
//                80.0, 75.0,
//                null, null,
//                22.3, 52.4
//        );
//
//        Server server = request.toEntity();
//
//        Mockito.when(serverRepository.existsServerByIphost(Mockito.anyString())).thenReturn(false); //중복은 iphost 로만 체크.
//
//        // save() 호출 시 serverNo를 자동으로 할당하는 mock 설정
//        Mockito.doAnswer(invocation -> {
//            Server s = invocation.getArgument(0); // save()에 전달된 첫 번째 인자 가져옴.
//            // DB처럼 serverNo를 자동으로 설정하지 않고, mock에서 임시로 serverNo를 반환
//            return new Server(1L, s.getServerId(), s.getCpuUsageThreshold(), s.getCpuTemperatureThreshold(),
//                    s.getMemoryUsageThreshold(), s.getMemoryTemperatureThreshold(),
//                    s.getDiskUsageThreshold(), s.getDiskTemperatureThreshold(), s.getIphost(), s.getCompanyDomain());
//        }).when(serverRepository.save(Mockito.any(Server.class)));
//
////        Mockito.when(serverRepository.findServerByIphost(Mockito.anyString())).thenReturn(Optional.of(server));
//
//        Server savedServer = serverServiceImpl.register(request);
//
//        Mockito.verify(serverRepository, Mockito.times(1)).existsServerByIphost(Mockito.anyString());
//
////        Assertions.assertAll(){
////            () -> Assertions.assertEquals(1L, savedsServer.getServerNo());
////
////        }
//
//
//
//
//
////        // when: MockMvc를 사용해 HTTP 요청을 보내는 것 시뮬레이션
////        String json = new ObjectMapper().writeValueAsString(request); // 자바 객체를 json으로 변환
////
////        mockMvc.perform(post("/servers") //post 요청 보내는 흉내를 냄
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(json))
////                .andExpect(status().isCreated())
////                .andExpect(jsonPath("$.iphost").value("192.168.0.1")) //.andExpect(...)들은 응답으로 돌아온 JSON 결과나 HTTP 상태코드를 검증
////                .andExpect(jsonPath("$.companyDomain").value("javaMe.com"))
////                .andExpect(jsonPath("$.serverNo").value(1))
////                .andExpect(jsonPath("$.cpuUsageThreshold").value(80.0))
////                .andExpect(jsonPath("$.cpuTemperatureThreshold").value(75.9))
////                .andExpect(jsonPath("$.memoryUsageThreshold").value())
////                .andExpect(jsonPath("$.memoryTemperatureThreshold").value(null))
////                .andExpect(jsonPath("$.diskUsageThreshold").value(22.3))
////                .andExpect(jsonPath("$.diskTemperatureThreshold").value(52.4)
////                );
//
//
//
//
//
//    }
//
//
//
//}
