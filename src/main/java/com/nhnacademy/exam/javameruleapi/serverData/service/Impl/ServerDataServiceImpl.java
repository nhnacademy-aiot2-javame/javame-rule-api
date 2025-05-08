package com.nhnacademy.exam.javameruleapi.serverData.service.Impl;


import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.serverData.repository.ServerDataRepository;
import com.nhnacademy.exam.javameruleapi.serverData.service.ServerDataService;
import com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception.AlreadyServerDataExistsException;
import com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception.ServerDataNotExistsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */
@Slf4j
@Service
@Transactional
public class ServerDataServiceImpl implements ServerDataService {

    private final ServerDataRepository serverDataRepository;

    public ServerDataServiceImpl(ServerDataRepository serverDataRepository) {
        this.serverDataRepository = serverDataRepository;
    }

    ServerDataResponse responseMapper(ServerData serverData){
        return new ServerDataResponse(
                serverData.getServerDataNo(), serverData.getIphost(),
                serverData.getServerDataCategory(), serverData.getServerDataTopic(),
                serverData.getMinThreshold(), serverData.getMaxThreshold()
        );
    }


    @Override
    public ServerDataResponse registerServerData(ServerDataRegisterRequest serverDataRegisterRequest) {
        Boolean optionalServerData = serverDataRepository.existsServerDataByIphost(serverDataRegisterRequest.getIphost());
        if(optionalServerData){
            throw new AlreadyServerDataExistsException("이미 존재하는 서버 데이터 입니다.");
        }
          ServerData serverData = new ServerData(
                  serverDataRegisterRequest.getIphost(),
                  serverDataRegisterRequest.getServerDataCategory(),
                  serverDataRegisterRequest.getServerDataTopic(),
                  serverDataRegisterRequest.getMinThreshold(),
                  serverDataRegisterRequest.getMaxThreshold(),
                  serverDataRegisterRequest.getCompanyDomain()
          );
            ServerData savedServerData = serverDataRepository.save(serverData);
        return responseMapper(savedServerData);
    }

    @Override
    public ServerDataResponse getServerData(long serverDataNo) {
        Boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if(!optionalServerData){
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }
        ServerData serverData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        return responseMapper(serverData);
    }

    @Override
    public ServerDataResponse updateServerData(long serverDataNo, ServerDataUpdateRequest serverDataUpdateRequest) {
        Boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if(!optionalServerData){
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }
        ServerData updateTargetServerData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        updateTargetServerData.update(
                serverDataUpdateRequest.getServerDataCategory(),
                serverDataUpdateRequest.getServerDataTopic(),
                serverDataUpdateRequest.getMinThreshold(),
                serverDataUpdateRequest.getMaxThreshold()
        );
        ServerData updatedServerData = serverDataRepository.save(updateTargetServerData);
        log.debug("updatedServerData:{}", updatedServerData);

        return responseMapper(updatedServerData);
    }

    @Override
    public Void delete(long serverDataNo) {
        Boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if(!optionalServerData){
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }

        ServerData deleteTargetServerData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        serverDataRepository.delete(deleteTargetServerData);
        return null;
    }
}
