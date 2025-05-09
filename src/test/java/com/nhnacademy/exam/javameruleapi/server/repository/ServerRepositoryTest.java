package com.nhnacademy.exam.javameruleapi.server.repository;

import com.nhnacademy.exam.javameruleapi.server.common.Exception.ServerNotExistException;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
    void registerServer(){
        Server server2 = Server.ofNewServer(

                "192.168.37.4",
                "javaMe"
        );
        serverRepository.save(server2);

        Optional<Server> optionalServer = serverRepository.getServerByIphost("192.168.37.4");
        Assertions.assertTrue(optionalServer.isPresent());

        Server server = optionalServer.get();
        Assertions.assertAll(
                ()->Assertions.assertEquals(server2.getServerNo(), server.getServerNo()),
                ()->Assertions.assertEquals(server2.getIphost(), server.getIphost()),
                ()->Assertions.assertEquals(server2.getCompanyDomain(), server.getCompanyDomain())
        );


    }


    @Test
    @DisplayName("ip주소로 서버찾기")
    void findServerByIphost(){
        Optional<Server> optionalServer = serverRepository.getServerByIphost("192.168.32.5");
        Assertions.assertTrue(optionalServer.isPresent());

        Server found = optionalServer.get();
        Assertions.assertAll(
                ()-> Assertions.assertEquals("192.168.32.5", found.getIphost()),
                ()-> Assertions.assertEquals("javaMe", found.getCompanyDomain())
        );
    }


    @Test
    @DisplayName("회사 도메인에 등록된 서버목록 조회")
    void getServersByCompanyDomain(){

        List<Server> servers = serverRepository.getServersByCompanyDomain("javaMe")
                .orElseThrow(()-> new ServerNotExistException("서버 목록이 존재하지 않습니다."));

        // 반환된 서버 리스트 크기가 5인지 확인.
        Assertions.assertEquals(5, servers.size(), "서버 리스트의 크기가 예상과 다릅니다.");

        // 리스트의 모든 서버가 도메인을 javaMe로 가지고 있는지 확인
        servers.forEach(server -> {
            Assertions.assertEquals("javaMe", server.getCompanyDomain()," 서버의 도메인이 javaMe가 아닙니다.");
                }
        );
        //


    }

    @Test
    @DisplayName("서버 정보 업데이트")
    void update(){
        Optional<Server> optionalServer = serverRepository.getServerByIphost("192.168.32.3");
        Assertions.assertTrue(optionalServer.isPresent());
        Server targetServer = optionalServer.get();

        targetServer.update(
                "200.33.5"
        );

        Optional<Server> optionalServer2 = serverRepository.getServerByIphost("200.33.5");
        Assertions.assertTrue(optionalServer2.isPresent());
        Server found = optionalServer2.get();

        Assertions.assertAll(
                ()-> Assertions.assertEquals(targetServer.getServerNo(), found.getServerNo()),
                ()-> Assertions.assertEquals("200.33.5", found.getIphost()),
                ()-> Assertions.assertEquals("javaMe", found.getCompanyDomain())
        );
    }


    @Test
    @DisplayName("서버 삭제")
    void delete(){
        serverRepository.deleteServerByIphost("173.224.89.1");
        Optional<Server> optionalServer = serverRepository.getServerByIphost("173.224.89.1");
        Assertions.assertTrue(optionalServer.isEmpty());

    }

}
