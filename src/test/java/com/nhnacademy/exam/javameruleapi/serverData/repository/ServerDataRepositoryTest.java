package com.nhnacademy.exam.javameruleapi.serverData.repository;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Slf4j
@ActiveProfiles("test")
@Transactional
public class ServerDataRepositoryTest {

    @Autowired
    public ServerDataRepository serverDataRepository;

    @Autowired
    public ServerRepository serverRepository;


    private List<Server> servers;
    private ServerData serverData;
    private ServerData savedServerData;

    @BeforeEach
    void setUp() {
        servers = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Server server = Server.ofNewServer(

                    "192.168.32.%d".formatted(i),
                    "javaMe"
            );

            serverRepository.save(server);
            servers.add(server);
        }
        Server server2 = Server.ofNewServer(

                "173.224.89.1",
                "javaYou"
        );
        serverRepository.save(server2);
        servers.add(server2);

        ServerDataRegisterRequest serverDataRegisterRequest = new ServerDataRegisterRequest(
                1, "power_meter", "modbus",
                "power_watts", 80.0, 99.9
        );
        Optional<Server> optionalFoundServer = serverRepository.getServerByServerNo(servers.get(1).getServerNo());
        Server foundServer = optionalFoundServer.get();


        serverData = new ServerData(
                foundServer,
                serverDataRegisterRequest.getServerDataLocation(),
                serverDataRegisterRequest.getServerDataGateway(),
                serverDataRegisterRequest.getServerDataName(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold()
        );

        foundServer.addServerData(serverData);
        savedServerData = serverDataRepository.save(serverData);


    }


    @Test
    @DisplayName("서버 데이터 등록")
    void registerServerData() {
        ServerDataRegisterRequest serverDataRegisterRequest = new ServerDataRegisterRequest(
                servers.get(1).getServerNo(), "server_resource_data", "modbus",
                "current_amps", 10.2, 89.9
        );

        Boolean isExist = serverDataRepository.existsByServer_ServerNoAndServerDataName(
                serverDataRegisterRequest.getServerNo(), serverDataRegisterRequest.getServerDataName());
        Assertions.assertFalse(isExist);

        Optional<Server> optionalServer = serverRepository.getServerByServerNo(serverDataRegisterRequest.getServerNo());
        Server foundServer = optionalServer.get();

        ServerData serverData2 = new ServerData(
                foundServer,
                serverDataRegisterRequest.getServerDataLocation(),
                serverDataRegisterRequest.getServerDataGateway(),
                serverDataRegisterRequest.getServerDataName(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold()
        );

        serverDataRepository.save(serverData2);

        Assertions.assertAll(
                () -> Assertions.assertEquals("server_resource_data", serverData2.getServerDataLocation()),
                () -> Assertions.assertEquals("modbus", serverData2.getServerDataGateway()),
                () -> Assertions.assertEquals("current_amps", serverData2.getServerDataName()),
                () -> Assertions.assertEquals(10.2, serverData2.getMinThreshold()),
                () -> Assertions.assertEquals(89.9, serverData2.getMaxThreshold())
        );
    }

    @Test
    @DisplayName("서버 데이터 조회 - 서버 데이터 번호")
    void getServerDataByServerDataNo() {
        Boolean isExist = serverDataRepository.existsServerDataByServerDataNo(savedServerData.getServerDataNo());
        Assertions.assertTrue(isExist);

        ServerData foundServerData = serverDataRepository.getServerDataByServerDataNo(savedServerData.getServerDataNo());

        Assertions.assertAll(
                () -> Assertions.assertEquals("power_meter", foundServerData.getServerDataLocation()),
                () -> Assertions.assertEquals("modbus", foundServerData.getServerDataGateway()),
                () -> Assertions.assertEquals("power_watts", foundServerData.getServerDataName()),
                () -> Assertions.assertEquals(80.0, foundServerData.getMinThreshold()),
                () -> Assertions.assertEquals(99.9, foundServerData.getMaxThreshold())

        );
    }

    @Test
    @DisplayName("서버 데이터 리스트 조회 - 서버 번호 - 실패 케이스")
    void getServerDataListByServerNo() {
        Boolean isExist = serverRepository.existsServerByServerNo(3);
        Assertions.assertFalse(isExist); //서버가 존재한다는걸 확인.

        List<ServerData> serverDataList = serverDataRepository.getServerDataByServer_ServerNo(3);
        Assertions.assertTrue(serverDataList.isEmpty());
    }


    @Test
    @DisplayName("서버 데이터 업데이트")
    void updateServerData() {

        Boolean isExist = serverDataRepository.existsServerDataByServerDataNo(savedServerData.getServerDataNo());
        Assertions.assertTrue(isExist); //서버 데이터가 존재한다는 것을 확인.

        ServerData targetServerData = serverDataRepository.getServerDataByServerDataNo(savedServerData.getServerDataNo());
        Assertions.assertNotNull(targetServerData); // 업데이트 타켓 서버 데이터.

        ServerDataUpdateRequest serverDataUpdateRequest = new ServerDataUpdateRequest(
                "server_resource_data", "modbus",
                "current_amps", 5.0, 79.4);

        targetServerData.update(
                serverDataUpdateRequest.getServerDataLocation(),
                serverDataUpdateRequest.getServerDataGateway(),
                serverDataUpdateRequest.getServerDataName(),
                serverDataUpdateRequest.getMinThreshold(),
                serverDataUpdateRequest.getMaxThreshold()
        );

        ServerData updatedServerData = serverDataRepository.save(targetServerData);
        Assertions.assertAll(
                () -> Assertions.assertEquals("server_resource_data", updatedServerData.getServerDataLocation()),
                () -> Assertions.assertEquals("modbus", updatedServerData.getServerDataGateway()),
                () -> Assertions.assertEquals("current_amps", updatedServerData.getServerDataName()),
                () -> Assertions.assertEquals(5.0, updatedServerData.getMinThreshold()),
                () -> Assertions.assertEquals(79.4, updatedServerData.getMaxThreshold())
        );

    }

    @Test
    @DisplayName("서버 데이터 삭제")
    void deleteServerData() {
        Boolean isExist = serverDataRepository.existsServerDataByServerDataNo(savedServerData.getServerDataNo());
        Assertions.assertTrue(isExist); // 서버 데이터가 존재하는지 확인

        ServerData targetServerData = serverDataRepository.getServerDataByServerDataNo(savedServerData.getServerDataNo());
        Assertions.assertNotNull(targetServerData);

        serverDataRepository.delete(targetServerData);

        Assertions.assertNull(serverDataRepository.getServerDataByServerDataNo(savedServerData.getServerDataNo()));

    }

}
