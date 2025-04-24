package com.nhnacademy.exam.javameruleapi.server.repository;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import lombok.RequiredArgsConstructor;
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
public class ServerRepositoryTest {

    @Autowired
    private ServerRepository serverRepository;


    @BeforeEach
    void setUp(){
        List<Server> servers = new ArrayList<>();

        for(int i = 1; i<6; i++) {
            Server server = new Server(
                    "main_server%d".formatted(i),
                    33.4,
                    null,
                    24.5,
                    null,
                    44.0,
                    34.2,
                    "192.168.32.%d".formatted(i),
                    "javaMe"
            );

            serverRepository.save(server);
            servers.add(server);
        }

        Server server2 = new Server(
                "spare_server",
                67.2,
                89.4,
                null,
                null,
                null,
                null,
                "173.224.89.1",
                "javaYou"
        );
        serverRepository.save(server2);
        servers.add(server2);

    }

    @Test
    @DisplayName("서버 등록")
    void registerServer(){
        Server server2 = new Server(
                "main_server",
                33.4,
                null,
                24.5,
                null,
                44.0,
                34.2,
                "192.168.37.4",
                "javaMe"
        );
        serverRepository.save(server2);

        Optional<Server> optional = serverRepository.getServerByIphost("192.168.37.4");
        Assertions.assertTrue(optional.isPresent());

        Server server = optional.get();
        Assertions.assertAll(
                ()->Assertions.assertEquals(server2.getServerId(), server.getServerId()),
                ()->Assertions.assertEquals(server2.getServerNo(), server.getServerNo()),
                ()->Assertions.assertEquals(server2.getCpuTemperatureThreshold(), server.getCpuTemperatureThreshold()),
                ()->Assertions.assertEquals(server2.getCpuUsageThreshold(), server.getCpuUsageThreshold()),
                ()->Assertions.assertEquals(server2.getDiskTemperatureThreshold(), server.getDiskTemperatureThreshold()),
                ()->Assertions.assertEquals(server2.getDiskUsageThreshold(), server.getDiskUsageThreshold()),
                ()->Assertions.assertEquals(server2.getMemoryUsageThreshold(), server.getMemoryUsageThreshold()),
                ()->Assertions.assertEquals(server2.getMemoryTemperatureThreshold(), server.getMemoryTemperatureThreshold()),
                ()->Assertions.assertEquals(server2.getIphost(), server.getIphost()),
                ()->Assertions.assertEquals(server2.getCompanyDomain(), server.getCompanyDomain())
        );


    }



    @Test
    @DisplayName("서버ID로 서버 찾기")
    void findServerById(){
        Optional<Server> optional = serverRepository.getServerByServerId("main_server4");
        Assertions.assertTrue(optional.isPresent());

        Server found = optional.get();
        Assertions.assertAll(
                ()-> Assertions.assertEquals("main_server4", found.getServerId()),
                ()-> Assertions.assertEquals(33.4, found.getCpuUsageThreshold()),
                ()-> Assertions.assertEquals(null, found.getCpuTemperatureThreshold()),
                ()-> Assertions.assertEquals(24.5, found.getMemoryUsageThreshold()),
                ()-> Assertions.assertEquals(null, found.getMemoryTemperatureThreshold()),
                ()-> Assertions.assertEquals(44.0, found.getDiskUsageThreshold()),
                ()-> Assertions.assertEquals(34.2, found.getDiskTemperatureThreshold()),
                ()-> Assertions.assertEquals("192.168.32.4", found.getIphost()),
                ()-> Assertions.assertEquals("javaMe", found.getCompanyDomain())
        );

    }



    @Test
    @DisplayName("ip주소로 서버찾기")
    void findServerByIphost(){
        Optional<Server> optional = serverRepository.getServerByIphost("192.168.32.5");
        Assertions.assertTrue(optional.isPresent());

        Server found = optional.get();
        Assertions.assertAll(
                ()-> Assertions.assertEquals("192.168.32.5", found.getIphost()),
                ()-> Assertions.assertEquals("main_server5", found.getServerId()),
                ()-> Assertions.assertEquals(33.4, found.getCpuUsageThreshold()),
                ()-> Assertions.assertEquals(null, found.getCpuTemperatureThreshold()),
                ()-> Assertions.assertEquals(24.5, found.getMemoryUsageThreshold()),
                ()-> Assertions.assertEquals(null, found.getMemoryTemperatureThreshold()),
                ()-> Assertions.assertEquals(44.0, found.getDiskUsageThreshold()),
                ()-> Assertions.assertEquals(34.2, found.getDiskTemperatureThreshold()),
                ()-> Assertions.assertEquals("javaMe", found.getCompanyDomain())
        );
    }


    @Test
    @DisplayName("회사 도메인에 등록된 서버목록 조회")
    void getServersByCompanyDomain(){

        List<Server> servers = serverRepository.getServersByCompanyDomain("javaMe");
        // 서버 목록이 비어있지 않음을 확인.
        Assertions.assertFalse(servers.isEmpty());

        // 반환된 서버 리스트 크기가 5인지 확인.
        Assertions.assertEquals(5, servers.size(), "서버 리스트의 크기가 예상과 다릅니다.");

        // 리스트의 모든 서버가 도메인을 javaMe로 가지고 있는지 확인
        servers.forEach(server -> {
            Assertions.assertEquals("javaMe", server.getCompanyDomain()," 서버의 도메인이 javaMe가 아닙니다.");
                }
        );
        //

        servers.forEach(server -> {
            Assertions.assertNotNull(server.getCpuUsageThreshold(), "서버의 cpu 사용량이 null입니다.");
            Assertions.assertNull(server.getMemoryTemperatureThreshold(), "서버의 memory사용량이 null이어야 합니다.");
        });

};

    @Test
    @DisplayName("서버 정보 업데이트")
    void update(){
        Optional<Server> optional = serverRepository.getServerByIphost("192.168.32.3");
        Assertions.assertTrue(optional.isPresent());
        Server targetServer = optional.get();

        targetServer.update(
                "main_server43",
                43.5,
                66.6,
                44.4,
                75.0,
                44.2,
                90.2
        );

        Optional<Server> optional2 = serverRepository.getServerByIphost("192.168.32.3");
        Assertions.assertTrue(optional2.isPresent());
        Server found = optional2.get();

        Assertions.assertAll(
                ()-> Assertions.assertEquals(targetServer.getServerNo(), found.getServerNo()),
                ()-> Assertions.assertEquals(targetServer.getServerId(), found.getServerId()),
                ()-> Assertions.assertEquals(43.5, found.getCpuUsageThreshold()),
                ()-> Assertions.assertEquals(66.6, found.getCpuTemperatureThreshold()),
                ()-> Assertions.assertEquals(44.4, found.getMemoryUsageThreshold()),
                ()-> Assertions.assertEquals(75.0, found.getMemoryTemperatureThreshold()),
                ()-> Assertions.assertEquals(44.2, found.getDiskUsageThreshold()),
                ()-> Assertions.assertEquals(90.2, found.getDiskTemperatureThreshold()),
                ()-> Assertions.assertEquals("192.168.32.3", found.getIphost()),
                ()-> Assertions.assertEquals("javaMe", found.getCompanyDomain())
        );
    }


    @Test
    @DisplayName("서버 삭제")
    void delete(){
        serverRepository.deleteServerByIphost("173.224.89.1");
        Optional<Server> optional = serverRepository.getServerByIphost("173.224.89.1");
        Assertions.assertTrue(optional.isEmpty());

    }

}
