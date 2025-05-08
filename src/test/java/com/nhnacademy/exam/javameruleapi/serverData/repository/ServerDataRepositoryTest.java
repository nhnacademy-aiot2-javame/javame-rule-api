package com.nhnacademy.exam.javameruleapi.serverData.repository;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
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
public class ServerDataRepositoryTest {

    @Autowired
    public ServerDataRepository serverDataRepository;

    @Autowired
    public ServerRepository serverRepository;


    private List<Server> servers;
    private ServerData serverData;
    private ServerData savedServerData;

    @BeforeEach
    void setUp(){
        servers = new ArrayList<>();

        for(int i = 1; i<6; i++) {
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
                "192.168.32.5", "Mail Server", "Network",
                20.0, 80.0, "nhn_academy"
        );
        serverData = new ServerData(
                serverDataRegisterRequest.getIphost(),
                serverDataRegisterRequest.getServerDataCategory(),
                serverDataRegisterRequest.getServerDataTopic(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold(),
                serverDataRegisterRequest.getCompanyDomain()
        );

        savedServerData = serverDataRepository.save(serverData);
    }


    @Test
    @DisplayName("서버 데이터 등록")
    void registerServerData(){
        ServerDataRegisterRequest serverDataRegisterRequest = new ServerDataRegisterRequest(
                "192.168.32.4", "DB Server", "Performance",
                10.0, 95.0, "nhn_academy"
        );

       Boolean isExist = serverDataRepository.existsServerDataByIphost("192.168.32.4");
       Assertions.assertFalse(isExist);

       ServerData serverData = new ServerData(
                serverDataRegisterRequest.getIphost(),
                serverDataRegisterRequest.getServerDataCategory(),
                serverDataRegisterRequest.getServerDataTopic(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold(),
                serverDataRegisterRequest.getCompanyDomain()
       );

       ServerData savedServerData = serverDataRepository.save(serverData);

       Assertions.assertAll(
               ()-> Assertions.assertNotNull(savedServerData.getServerDataNo()),
               ()-> Assertions.assertEquals("192.168.32.4", savedServerData.getIphost()),
               ()-> Assertions.assertEquals("DB Server", savedServerData.getServerDataCategory()),
               ()-> Assertions.assertEquals("Performance", savedServerData.getServerDataTopic()),
               ()-> Assertions.assertEquals(10.0, savedServerData.getMinThreshold()),
               ()-> Assertions.assertEquals(95.0, savedServerData.getMaxThreshold())
       );
    }

    @Test
    @DisplayName("서버 데이터 조회 - 서버 데이터 번호")
    void getServerDataByServerDataNo(){
        Boolean isExist = serverDataRepository.existsServerDataByServerDataNo(savedServerData.getServerDataNo());
        Assertions.assertTrue(isExist);

        ServerData foundServerData = serverDataRepository.getServerDataByServerDataNo(savedServerData.getServerDataNo());

        Assertions.assertNotNull(foundServerData.getServerDataNo());
        Assertions.assertAll(
                ()-> Assertions.assertEquals("192.168.32.5", foundServerData.getIphost()),
                ()-> Assertions.assertEquals("Mail Server", foundServerData.getServerDataCategory()),
                ()-> Assertions.assertEquals("Network", foundServerData.getServerDataTopic()),
                ()-> Assertions.assertEquals(20.0, foundServerData.getMinThreshold()),
                ()-> Assertions.assertEquals(80.0, foundServerData.getMaxThreshold())

        );
    }

    @Test
    @DisplayName("서버 데이터 조회 - iphost")
    void getServerDataByiphost(){
        Boolean isExist = serverDataRepository.existsServerDataByIphost(savedServerData.getIphost());
        Assertions.assertTrue(isExist);

        ServerData foundServerData = serverDataRepository.getServerDataByIphost(savedServerData.getIphost());

        Assertions.assertNotNull(foundServerData.getServerDataNo());
        Assertions.assertAll(
                ()-> Assertions.assertEquals("192.168.32.5", foundServerData.getIphost()),
                ()-> Assertions.assertEquals("Mail Server", foundServerData.getServerDataCategory()),
                ()-> Assertions.assertEquals("Network", foundServerData.getServerDataTopic()),
                ()-> Assertions.assertEquals(20.0, foundServerData.getMinThreshold()),
                ()-> Assertions.assertEquals(80.0, foundServerData.getMaxThreshold())
        );
    }

    @Test
    @DisplayName("서버 데이터 업데이트")
    void updateServerData(){

        Boolean isExist = serverDataRepository.existsServerDataByIphost(savedServerData.getIphost());
        Assertions.assertTrue(isExist);

        ServerData targetServerData = serverDataRepository.getServerDataByIphost(savedServerData.getIphost());
        Assertions.assertNotNull(targetServerData);

        ServerDataUpdateRequest serverDataUpdateRequest = new ServerDataUpdateRequest(
                "DB Server",  "Performance", 5.0, 79.4);

        targetServerData.update(
                serverDataUpdateRequest.getServerDataCategory(),
                serverDataUpdateRequest.getServerDataTopic(),
                serverDataUpdateRequest.getMinThreshold(),
                serverDataUpdateRequest.getMaxThreshold()
                );

        ServerData updatedServerData = serverDataRepository.save(targetServerData);
        Assertions.assertNotNull(updatedServerData.getServerDataNo());
        Assertions.assertAll(
                ()-> Assertions.assertEquals("192.168.32.5", updatedServerData.getIphost()),
                ()-> Assertions.assertEquals("DB Server", updatedServerData.getServerDataCategory()),
                ()-> Assertions.assertEquals("Performance", updatedServerData.getServerDataTopic()),
                ()-> Assertions.assertEquals(5.0, updatedServerData.getMinThreshold()),
                ()-> Assertions.assertEquals(79.4, updatedServerData.getMaxThreshold())
        );

    }

    @Test
    @DisplayName("서버 데이터 삭제")
    void deleteServerData(){
        Boolean isExist = serverDataRepository.existsServerDataByIphost(savedServerData.getIphost());
        Assertions.assertTrue(isExist);

        ServerData targetServerData = serverDataRepository.getServerDataByIphost(savedServerData.getIphost());
        Assertions.assertNotNull(targetServerData);

        serverDataRepository.delete(targetServerData);

        Assertions.assertNull(serverDataRepository.getServerDataByIphost(savedServerData.getIphost()));

    }

}
