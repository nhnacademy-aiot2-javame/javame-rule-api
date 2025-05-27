package com.nhnacademy.exam.javameruleapi.server.repository;

import com.nhnacademy.exam.javameruleapi.server.common.exception.ServerListNotExistsException;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;
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
    private List<Server> servers;

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

    }

    @Test
    @DisplayName("서버 등록")
    void registerServer() {
        Server server2 = Server.ofNewServer(

                "192.168.37.4",
                "javaMe"
        );
        serverRepository.save(server2);

        Optional<Server> optionalServer = serverRepository.getServerByServerNo(server2.getServerNo());
        Assertions.assertTrue(optionalServer.isPresent());

        Server server = optionalServer.get();
        Assertions.assertAll(
                () -> Assertions.assertEquals(server2.getServerNo(), server.getServerNo()),
                () -> Assertions.assertEquals(server2.getIphost(), server.getIphost()),
                () -> Assertions.assertEquals(server2.getCompanyDomain(), server.getCompanyDomain())
        );


    }


    @Test
    @DisplayName("회사 도메인에 등록된 서버목록 조회")
    void getServersByCompanyDomain() {

        List<Server> servers2 = serverRepository.getServersByCompanyDomain("javaMe")
                .orElseThrow(() -> new ServerListNotExistsException("서버 목록이 존재하지 않습니다."));

        // 반환된 서버 리스트 크기가 5인지 확인.
        Assertions.assertEquals(5, servers2.size(), "서버 리스트의 크기가 예상과 다릅니다.");

        // 리스트의 모든 서버가 도메인을 javaMe로 가지고 있는지 확인
        servers2.forEach(server -> {
                    Assertions.assertEquals("javaMe", server.getCompanyDomain(), " 서버의 도메인이 javaMe가 아닙니다.");
                }
        );
        //


    }

    @Test
    @DisplayName("서버 정보 업데이트")
    void update() {
        Optional<Server> optionalServer = serverRepository.getServerByServerNo(servers.get(2).getServerNo());
        Assertions.assertTrue(optionalServer.isPresent());
        Server targetServer = optionalServer.get();

        targetServer.update(
                "200.33.5"
        );

        Optional<Server> optionalServer2 = serverRepository.getServerByServerNo(targetServer.getServerNo());
        Assertions.assertTrue(optionalServer2.isPresent());
        Server found = optionalServer2.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(targetServer.getServerNo(), found.getServerNo()),
                () -> Assertions.assertEquals("200.33.5", found.getIphost()),
                () -> Assertions.assertEquals("javaMe", found.getCompanyDomain())
        );
    }


    @Test
    @DisplayName("서버 삭제")
    void delete() {
        Boolean isExist = serverRepository.existsServerByServerNo(servers.get(4).getServerNo());
        Assertions.assertTrue(isExist);

        Optional<Server> optionalServer = serverRepository.getServerByServerNo(servers.get(4).getServerNo());
        Assertions.assertTrue(optionalServer.isPresent());
        Server foundServer = optionalServer.get();

        serverRepository.deleteServerByServerNo(foundServer.getServerNo());

        Optional<Server> optionalDeletedServer = serverRepository.getServerByServerNo(foundServer.getServerNo());
        Assertions.assertTrue(optionalDeletedServer.isEmpty());

    }

}
