package com.nhnacademy.exam.javameruleapi.server.service.Impl;

import com.nhnacademy.exam.javameruleapi.server.common.exception.AlreadyServerExistException;
import com.nhnacademy.exam.javameruleapi.server.common.exception.NoServerExistException;
import com.nhnacademy.exam.javameruleapi.server.common.exception.ServerNotExistException;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;

import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import com.nhnacademy.javame.common.dto.server.ServerRegisterRequest;
import com.nhnacademy.javame.common.dto.server.ServerResponse;
import com.nhnacademy.javame.common.dto.server.ServerUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;


    /**
     * Server 엔티티로부터 데이터를 추출해 ServerResponse 객체로 변환하는 정적 팩토리 메서드.
     *
     * @param server 엔티티 객체
     * @return 변환된 ServerResponse 객체
     */
    ServerResponse responseMapper(Server server){
        return ServerResponse.builder()
                .serverNo(server.getServerNo())
                .companyDomain(server.getCompanyDomain())
                .iphost(server.getIphost())
                .createdAt(server.getCreatedAt())
                .build();
    }

    @Override
    public ServerResponse register(ServerRegisterRequest serverRegisterRequest) {
        Boolean isExist = serverRepository.existsByCompanyDomainAndIphost(
                serverRegisterRequest.getCompanyDomain(),serverRegisterRequest.getIphost());
        if(isExist){
            throw new AlreadyServerExistException("이미 존재하는 서버입니다.");
        }
        Server server = Server.ofNewServer(serverRegisterRequest.getIphost(), serverRegisterRequest.getCompanyDomain());
        Server savedServer = serverRepository.save(server);
        return responseMapper(savedServer);
    }

    @Override
    public ServerResponse getServer(long serverNo) {
        Server server = serverRepository.getServerByServerNo(serverNo).orElseThrow(()-> new ServerNotExistException("존재하지 않는 서버입니다."));
        return responseMapper(server);
    }


    @Override
    public List<ServerResponse> getServers(String companyDomain) {
        List<Server> servers = serverRepository.getServersByCompanyDomain(companyDomain).orElseThrow(()->new NoServerExistException("해당 도메인에 대한 서버 리스트가 존재하지 않습니다."));
        List<ServerResponse> serverResponses = new ArrayList<>();
        for (Server s : servers){
            ServerResponse serverReponse = responseMapper(s);
            serverResponses.add(serverReponse);
        }
        return serverResponses;
    }


    @Override
    public ServerResponse update(long serverNo, ServerUpdateRequest serverUpdateRequest) {
        Server server = serverRepository.getServerByServerNo(serverNo).orElseThrow(()-> new ServerNotExistException("서버가 존재하지 않습니다."));
        server.update(serverUpdateRequest.getIphost());
        return responseMapper(server);
    }

    @Override
    public Void delete(long serverNo) {
        Server targetServer = serverRepository.getServerByServerNo(serverNo).orElseThrow(()-> new ServerNotExistException("서버가 존재하지 않습니다."));
        serverRepository.delete(targetServer);
                return null;
    }


}
