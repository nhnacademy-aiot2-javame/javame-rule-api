//package com.nhnacademy.exam.javameruleapi.server.service.Impl;
//
//import com.nhnacademy.exam.javameruleapi.server.common.Exception.AlreadyServerExistException;
//import com.nhnacademy.exam.javameruleapi.server.domain.Server;
//import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
//import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;
//import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
//import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//@Transactional
//public class ServerServiceImpl implements ServerService {
//
//    private final ServerRepository serverRepository;
//
//    public ServerServiceImpl(ServerRepository serverRepository) {
//        this.serverRepository = serverRepository;
//    }
//
//
//    @Override
//    public ServerResponse register(String iphost, ServerRegisterRequest serverRegisterRequest) {
//        Boolean isExist = serverRepository.existsServerByIphost(iphost);
//        if(isExist){
//            throw new AlreadyServerExistException("이미 존재하는 서버입니다.");
//        }
//        Server server = serverRegisterRequest.toEntity();
//        serverRepository.save(server);
//        return ServerResponse.from(server);
//    }
//
//    @Override
//    public ServerResponse getServer(long serverNo) {
//        Server server = serverRepository.getServerByServerNo(serverNo);
//        return ServerResponse.from(server);
//    }
//
//    @Override
//    public List<ServerResponse> getServers(String companyDomain) {
//        List<Server> servers = serverRepository.getServersByCompanyDomain(companyDomain);
//        List<ServerResponse> serverResponses = new ArrayList<>();
//        for (Server s : servers){
//            ServerResponse serverReponse = ServerResponse.from(s);
//            serverResponses.add(serverReponse);
//        }
//        return serverResponses;
//    }
//
//
//    @Override
//    public ServerResponse update(long serverNo, ServerUpdateRequest serverUpdateRequest) {
//        Server server = serverRepository.getServerByServerNo(serverNo);
//        return ServerResponse.from(server);
//    }
//
//    @Override
//    public void delete(long serverNo) {
//        Server targetServer = serverRepository.getServerByServerNo(serverNo);
//        serverRepository.delete(targetServer);
//    }
//
//
//}
