package com.nhnacademy.exam.javameruleapi.server.service.Impl;

import com.nhnacademy.exam.javameruleapi.server.common.Exception.AlreadyExistException;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerThresholdRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerThresholdUpdateRequest;
import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public ServerResponse responseMapper(Server server){
        return new ServerResponse(Server.builder()
                .cpuUsageThreshold(server.getCpuUsageThreshold())
                )
    }


    @Override
    public ServerResponse register(ServerThresholdRegisterRequest serverThresholdRegisterRequest) {
        Boolean isExist = serverRepository.existsServerByServerNo(serverThresholdRegisterRequest.getServerNo());
        if(isExist){
            throw new AlreadyExistException("이미 존재하는 서버입니다.");
        }
        Server server = Server.builder()
                .cpuUsageThreshold(serverThresholdRegisterRequest.getCpuUsageThreshold())
                .cpuTemperatureThreshold(serverThresholdRegisterRequest.getCpuTemperatureThreshold())
                .memoryUsageThreshold(serverThresholdRegisterRequest.getMemoryUsageThreshold())
                .memoryTemperatureThreshold(serverThresholdRegisterRequest.getMemoryTemperatureThreshold())
                .diskUsageThreshold(serverThresholdRegisterRequest.getDiskUsageThreshold())
                .diskTemperatureThreshold(serverThresholdRegisterRequest.diskTemperatureThreshold)
                .build();

        serverRepository.save(server);


        return responseMapper(server);
    }

    @Override
    public ServerResponse getServerThreshold(long serverNo) {
        return null;
    }

    @Override
    public ServerResponse update(ServerThresholdUpdateRequest serverThresholdUpdateRequest) {
        return null;
    }

    @Override
    public Void delete(long serverNo) {
        return null;
    }
}
