package com.nhnacademy.exam.javameruleapi.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.exam.javameruleapi.server.common.Exception.AlreadyServerExistException;
import com.nhnacademy.exam.javameruleapi.server.common.Exception.ServerNotExistException;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;
import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
import com.nhnacademy.exam.javameruleapi.server.service.Impl.ServerServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ServerServiceImplTest {


    @Mock
    ServerRepository serverRepository;

    @InjectMocks //내가 테스트할 대상 클래스에 붙임.
    ServerServiceImpl serverServiceImpl; //테스트 할 진짜 서비스 객체, mock이 주입됨.

    private ServerRegisterRequest serverRegisterRequest;
    private Server server;



    @BeforeEach
    void setUp(){

        // 가짜 request 데이터 생성
        serverRegisterRequest = new ServerRegisterRequest(
                "192.168.0.1",
                "javaMe.com"
        );

        //mockServer 생성
        server = Server.ofNewServer(serverRegisterRequest.getIphost(), serverRegisterRequest.getCompanyDomain());

        ReflectionTestUtils.setField(server, "serverNo", 1L);
    }


    @Test
    @DisplayName("서버 등록")
    void registerServer() {

        Mockito.when(serverRepository.existsServerByIphost(Mockito.anyString())).thenReturn(false); //중복은 iphost 로만 체크.
        Mockito.when(serverRepository.save(Mockito.any(Server.class))).thenReturn(server);

        // 실제 서비스 메서드 호출
        ServerResponse serverResponse = serverServiceImpl.register(serverRegisterRequest); // 여기서야 비로소 실제 serverNo값이 바뀔거임.
        log.debug("serverResponse:{}", serverResponse);

        // existsServerByIphost가 한 번만 호출되었는지 확인
        Mockito.verify(serverRepository, Mockito.times(1)).existsServerByIphost(Mockito.anyString());

        // 서버가 올바른 서버 넘버로 저장되었는지 확인
        Assertions.assertNotNull(serverResponse.getServerNo());
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1L, serverResponse.getServerNo()),
                ()-> Assertions.assertEquals("192.168.0.1", serverResponse.getIphost()),
                ()-> Assertions.assertEquals("javaMe.com", serverResponse.getCompanyDomain())
        );
    }


    @Test
    @DisplayName("서버 등록 - ip 주소 중복 확인")
    void registerServer_exception_case1(){
        Mockito.when(serverRepository.existsServerByIphost(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(AlreadyServerExistException.class, ()->{
            serverServiceImpl.register(serverRegisterRequest);
        });

        Mockito.verify(serverRepository, Mockito.times(1)).existsServerByIphost(Mockito.anyString());
        Mockito.verify(serverRepository, Mockito.never()).save(Mockito.any(Server.class));
    }


    @Test
    @DisplayName("서버 번호로 서버 조회")
    void getServer(){

        Mockito.when(serverRepository.getServerByServerNo(Mockito.anyLong())).thenReturn(Optional.of(server));

        ServerResponse serverResponse = serverServiceImpl.getServer(server.getServerNo());

        Mockito.verify(serverRepository, Mockito.times(1)).getServerByServerNo(Mockito.anyLong());

        Assertions.assertNotNull(serverResponse.getServerNo());
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1L, serverResponse.getServerNo()),
                ()-> Assertions.assertEquals("192.168.0.1",serverResponse.getIphost()),
                ()-> Assertions.assertEquals("javaMe.com", serverResponse.getCompanyDomain())
        );

    }

    @Test
    @DisplayName("서버 번호로 서버 조회 - 서버 번호 null 체크 ")
    void getServer_exception_case1(){

        Mockito.when(serverRepository.getServerByServerNo(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ServerNotExistException.class, ()->{
            serverServiceImpl.getServer(server.getServerNo());
        });

        Mockito.verify(serverRepository, Mockito.times(1)).getServerByServerNo(Mockito.anyLong());
    }



    @Test
    @DisplayName("회사 도메인으로 등록된 서버목록 조회")
    void getServers(){

        List<Server> servers = new ArrayList<>();
        servers.add(server);

        Mockito.when(serverRepository.getServersByCompanyDomain(Mockito.anyString()))
                .thenReturn(Optional.of(servers));

        List<ServerResponse> serverResponses = serverServiceImpl.getServers(serverRegisterRequest.getCompanyDomain());
        log.debug("serverResponses:{}", serverResponses);

        Mockito.verify(serverRepository, Mockito.times(1)).getServersByCompanyDomain(Mockito.anyString());

        Assertions.assertEquals(serverResponses.size(),1, "해당 회사 도메인으로 조회된 서버가 1개 아닙니다.");

        serverResponses.forEach(serverResponse -> {
            Assertions.assertEquals("javaMe.com", serverResponse.getCompanyDomain(), "서버의 도메인이 javaMe.com 이 아닙니다.");
        });
    }

    @Test
    @DisplayName("서버 수정")
    void update() {

        Mockito.when(serverRepository.getServerByServerNo(Mockito.anyLong())).thenReturn(Optional.of(server));

        ServerUpdateRequest serverUpdateRequest = new ServerUpdateRequest("333.2444.22");

        ServerResponse serverResponse = serverServiceImpl.update(server.getServerNo(), serverUpdateRequest);
        log.debug("serverResponse:{}",serverResponse);

        Mockito.verify(serverRepository, Mockito.times(1)).getServerByServerNo(Mockito.anyLong());

        Assertions.assertNotNull(serverResponse.getServerNo());
        Assertions.assertAll(
                ()-> Assertions.assertEquals("333.2444.22", serverResponse.getIphost()),
                ()-> Assertions.assertEquals("javaMe.com", serverResponse.getCompanyDomain())
        );
    }

    @Test
    @DisplayName("서버 삭제")
    void delete() {

        Mockito.when(serverRepository.getServerByServerNo(Mockito.anyLong())).thenReturn(Optional.of(server));
        Mockito.doNothing().when(serverRepository).delete(Mockito.any(Server.class));

        Void serverResponse = serverServiceImpl.delete(server.getServerNo());

        Mockito.verify(serverRepository, Mockito.times(1)).getServerByServerNo(Mockito.anyLong());

        Assertions.assertNull(serverResponse);
    }








    }




