package com.nhnacademy.exam.javameruleapi.serverData.service;

import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.serverData.repository.ServerDataRepository;
import com.nhnacademy.exam.javameruleapi.serverData.service.Impl.ServerDataServiceImpl;
import com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception.AlreadyServerDataExistsException;
import com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception.ServerDataNotExistsException;
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

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ServerDataServiceImplTest {


    @Mock
    ServerDataRepository serverDataRepository;

    @InjectMocks
    ServerDataServiceImpl serverDataServiceImpl;

    private ServerData serverData;
    private ServerDataRegisterRequest serverDataRegisterRequest;

    @BeforeEach
    void setUp(){

        serverDataRegisterRequest = new ServerDataRegisterRequest
                ("192.168.32.5", "Mail Server", "Network", 20.0, 80.0
        );

        serverData = new ServerData(
                serverDataRegisterRequest.getIphost(),
                serverDataRegisterRequest.getServerDataCategory(),
                serverDataRegisterRequest.getServerDataTopic(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold());

        ReflectionTestUtils.setField(serverData, "serverDataNo", 1L);
        log.debug("serverData.getServerDataNo():{}", serverData.getServerDataNo());
    }



    @Test
    @DisplayName("서버 데이터 등록")
    void registerServerData() {

        Mockito.when(serverDataRepository.existsServerDataByIphost(Mockito.anyString())).thenReturn(false);
        Mockito.when(serverDataRepository.save(Mockito.any(ServerData.class))).thenReturn(serverData);

        //실제 서비스 메서드 호출
        ServerDataResponse serverDataResponse = serverDataServiceImpl.registerServerData(serverDataRegisterRequest);

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByIphost(Mockito.anyString());

        Assertions.assertNotNull(serverDataResponse.getServerDataNo());
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1L, serverDataResponse.getServerDataNo()),
                ()-> Assertions.assertEquals("192.168.32.5", serverDataResponse.getIphost()),
                ()-> Assertions.assertEquals("Mail Server", serverDataResponse.getServerDataCategory()),
                ()-> Assertions.assertEquals("Network", serverDataResponse.getServerDataTopic()),
                ()-> Assertions.assertEquals(20.0, serverDataResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(80.0, serverDataResponse.getMaxThreshold())
        );

    }


    @Test
    @DisplayName("서버 데이터 등록 - ip 주소 중복 확인")
    void registerServerData_exception_case1(){
        Mockito.when(serverDataRepository.existsServerDataByIphost(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(AlreadyServerDataExistsException.class, ()->{
            serverDataServiceImpl.registerServerData(serverDataRegisterRequest);
        });

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByIphost(Mockito.anyString());
        Mockito.verify(serverDataRepository, Mockito.never()).save(Mockito.any(ServerData.class));

    }


    @Test
    @DisplayName("서버 데이터 번호로 서버 데이터 조회")
    void getServerData() {

        Mockito.when(serverDataRepository.existsServerDataByServerDataNo(Mockito.anyLong())).thenReturn(true);
        Mockito.when(serverDataRepository.getServerDataByServerDataNo(Mockito.anyLong())).thenReturn(serverData);

        ServerDataResponse serverDataResponse = serverDataServiceImpl.getServerData(serverData.getServerDataNo());

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByServerDataNo(Mockito.anyLong());
        Mockito.verify(serverDataRepository, Mockito.times(1)).getServerDataByServerDataNo(Mockito.anyLong());

        Assertions.assertNotNull(serverDataResponse);
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1, serverDataResponse.getServerDataNo()),
                ()-> Assertions.assertEquals("192.168.32.5", serverDataResponse.getIphost()),
                ()-> Assertions.assertEquals("Mail Server", serverDataResponse.getServerDataCategory()),
                ()-> Assertions.assertEquals("Network", serverDataResponse.getServerDataTopic()),
                ()-> Assertions.assertEquals(20.0, serverDataResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(80.0, serverDataResponse.getMaxThreshold())
        );
    }


    @Test
    @DisplayName("서버 데이터 번호로 서버 데이터 조회 - 서버 데이터 번호 중복 확인")
    void getServerData_exception_case1(){
        Mockito.when(serverDataRepository.existsServerDataByServerDataNo(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(ServerDataNotExistsException.class, ()->{
            serverDataServiceImpl.getServerData(serverData.getServerDataNo());
        });

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByServerDataNo(Mockito.anyLong());
        Mockito.verify(serverDataRepository, Mockito.never()).getServerDataByServerDataNo(Mockito.anyLong());
    }


    @Test
    @DisplayName("서버 데이터 수정")
    void updateServerData() {

        ServerDataUpdateRequest serverDataUpdateRequest = new ServerDataUpdateRequest(
                "Data Server",
                "Usage",
                45.2,
                87.8
        );

        Mockito.when(serverDataRepository.existsServerDataByServerDataNo(Mockito.anyLong())).thenReturn(true);
        Mockito.when(serverDataRepository.getServerDataByServerDataNo(Mockito.anyLong())).thenReturn(serverData);
        Mockito.when(serverDataRepository.save(Mockito.any(ServerData.class))).thenReturn(serverData);

        ServerDataResponse serverDataResponse = serverDataServiceImpl.updateServerData(serverData.getServerDataNo(), serverDataUpdateRequest);

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByServerDataNo(Mockito.anyLong());
        Mockito.verify(serverDataRepository, Mockito.times(1)).getServerDataByServerDataNo(Mockito.anyLong());

        Assertions.assertNotNull(serverDataResponse);
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1, serverDataResponse.getServerDataNo()),
                ()-> Assertions.assertEquals("192.168.32.5", serverDataResponse.getIphost()),
                ()-> Assertions.assertEquals("Data Server", serverDataResponse.getServerDataCategory()),
                ()-> Assertions.assertEquals("Usage", serverDataResponse.getServerDataTopic()),
                ()-> Assertions.assertEquals(45.2, serverDataResponse.getMinThreshold()),
                ()-> Assertions.assertEquals(87.8, serverDataResponse.getMaxThreshold())
        );
    }



    @Test
    @DisplayName("서버 데이터 삭제")
    void deleteServerData() {
        Mockito.when(serverDataRepository.existsServerDataByServerDataNo(Mockito.anyLong())).thenReturn(true);
        Mockito.when(serverDataRepository.getServerDataByServerDataNo(Mockito.anyLong())).thenReturn(serverData);
        Mockito.doNothing().when(serverDataRepository).delete(Mockito.any(ServerData.class));

        Void serverDataResponse = serverDataServiceImpl.delete(serverData.getServerDataNo());

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByServerDataNo(Mockito.anyLong());
        Mockito.verify(serverDataRepository, Mockito.times(1)).getServerDataByServerDataNo(Mockito.anyLong());

        Assertions.assertNull(serverDataResponse);

    }
}
