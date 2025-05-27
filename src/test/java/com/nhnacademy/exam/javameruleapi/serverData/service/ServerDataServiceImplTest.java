package com.nhnacademy.exam.javameruleapi.serverData.service;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
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

import java.util.Optional;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ServerDataServiceImplTest {


    @Mock
    ServerDataRepository serverDataRepository;

    @Mock
    ServerRepository serverRepository;

    @InjectMocks
    ServerDataServiceImpl serverDataServiceImpl;

    private ServerData serverData;
    private ServerDataRegisterRequest serverDataRegisterRequest;
    private Server server;

    @BeforeEach
    void setUp() {

        server = new Server(
                "192.168.32.1", "javaMe"
        );

        serverDataRegisterRequest = new ServerDataRegisterRequest
                (1, "power_meter", "modbus",
                        "power_watts", 80.0, 99.9
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
    void registerServerData() {

        Mockito.when(serverDataRepository.existsByServer_ServerNoAndServerDataName(
                Mockito.anyLong(), Mockito.anyString())).thenReturn(false);
        Mockito.when(serverDataRepository.save(Mockito.any(ServerData.class))).thenReturn(serverData);
        Mockito.when(serverRepository.getServerByServerNo(Mockito.anyLong())).thenReturn(Optional.of(server));
        //실제 서비스 메서드 호출
        ServerDataResponse serverDataResponse = serverDataServiceImpl.registerServerData(
                server.getServerNo(), serverDataRegisterRequest);

        Mockito.verify(serverDataRepository, Mockito.times(1))
                .existsByServer_ServerNoAndServerDataName(Mockito.anyLong(), Mockito.anyString());

        Assertions.assertNotNull(serverDataResponse.getServerDataNo());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, serverDataResponse.getServerDataNo()),
                () -> Assertions.assertEquals("power_meter", serverDataResponse.getServerDataLocation()),
                () -> Assertions.assertEquals("modbus", serverDataResponse.getServerDataGateway()),
                () -> Assertions.assertEquals("power_watts", serverDataResponse.getServerDataName()),
                () -> Assertions.assertEquals(80.0, serverDataResponse.getMinThreshold()),
                () -> Assertions.assertEquals(99.9, serverDataResponse.getMaxThreshold())
        );

    }


    @Test
    @DisplayName("서버 데이터 등록 - ip 주소 중복 확인")
    void registerServerData_exception_case1() {
        Mockito.when(serverDataRepository.existsByServer_ServerNoAndServerDataName(
                Mockito.anyLong(), Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(AlreadyServerDataExistsException.class, () -> {
            serverDataServiceImpl.registerServerData(server.getServerNo(), serverDataRegisterRequest);
        });

        Mockito.verify(serverDataRepository, Mockito.times(1))
                .existsByServer_ServerNoAndServerDataName(Mockito.anyLong(), Mockito.anyString());
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
                () -> Assertions.assertEquals(1, serverDataResponse.getServerDataNo()),
                () -> Assertions.assertEquals("power_meter", serverDataResponse.getServerDataLocation()),
                () -> Assertions.assertEquals("modbus", serverDataResponse.getServerDataGateway()),
                () -> Assertions.assertEquals("power_watts", serverDataResponse.getServerDataName()),
                () -> Assertions.assertEquals(80.0, serverDataResponse.getMinThreshold()),
                () -> Assertions.assertEquals(99.9, serverDataResponse.getMaxThreshold())
        );
    }


    @Test
    @DisplayName("서버 데이터 번호로 서버 데이터 조회 - 서버 데이터 번호 중복 확인")
    void getServerData_exception_case1() {
        Mockito.when(serverDataRepository.existsServerDataByServerDataNo(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(ServerDataNotExistsException.class, () -> {
            serverDataServiceImpl.getServerData(serverData.getServerDataNo());
        });

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByServerDataNo(Mockito.anyLong());
        Mockito.verify(serverDataRepository, Mockito.never()).getServerDataByServerDataNo(Mockito.anyLong());
    }


    @Test
    @DisplayName("서버 데이터 수정")
    void updateServerData() {

        ServerDataUpdateRequest serverDataUpdateRequest = new ServerDataUpdateRequest(
                "power_meter",
                "modbus1",
                "power_watts",
                45.8,
                82.0
        );

        Mockito.when(serverDataRepository.existsServerDataByServerDataNo(Mockito.anyLong())).thenReturn(true);
        Mockito.when(serverDataRepository.getServerDataByServerDataNo(Mockito.anyLong())).thenReturn(serverData);
        Mockito.when(serverDataRepository.save(Mockito.any(ServerData.class))).thenReturn(serverData);

        ServerDataResponse serverDataResponse = serverDataServiceImpl.updateServerData(serverData.getServerDataNo(), serverDataUpdateRequest);

        Mockito.verify(serverDataRepository, Mockito.times(1)).existsServerDataByServerDataNo(Mockito.anyLong());
        Mockito.verify(serverDataRepository, Mockito.times(1)).getServerDataByServerDataNo(Mockito.anyLong());

        Assertions.assertNotNull(serverDataResponse);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, serverDataResponse.getServerDataNo()),
                () -> Assertions.assertEquals("power_meter", serverDataResponse.getServerDataLocation()),
                () -> Assertions.assertEquals("modbus1", serverDataResponse.getServerDataGateway()),
                () -> Assertions.assertEquals("power_watts", serverDataResponse.getServerDataName()),
                () -> Assertions.assertEquals(45.8, serverDataResponse.getMinThreshold()),
                () -> Assertions.assertEquals(82.0, serverDataResponse.getMaxThreshold())
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
