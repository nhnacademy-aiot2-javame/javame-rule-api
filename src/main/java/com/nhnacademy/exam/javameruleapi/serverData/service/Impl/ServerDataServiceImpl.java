package com.nhnacademy.exam.javameruleapi.serverData.service.Impl;


import com.nhnacademy.exam.javameruleapi.server.common.exception.ServerNotExistException;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.repository.ServerRepository;
import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;

import com.nhnacademy.exam.javameruleapi.serverData.repository.ServerDataRepository;
import com.nhnacademy.exam.javameruleapi.serverData.service.ServerDataService;
import com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception.AlreadyServerDataExistsException;
import com.nhnacademy.exam.javameruleapi.serverData.service.common.Exception.ServerDataNotExistsException;
import com.nhnacademy.javame.common.dto.serverData.ServerDataRegisterRequest;
import com.nhnacademy.javame.common.dto.serverData.ServerDataResponse;
import com.nhnacademy.javame.common.dto.serverData.ServerDataUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * ServerDataService 인터페이스의 구현체로, 서버 데이터에 대한 등록, 조회, 수정, 삭제 비즈니스 로직을 담당합니다.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerDataServiceImpl implements ServerDataService {

    /**
     * 서버 데이터에 대한 CRUD 작업을 수행하는 JPA 레포지토리.
     */
    private final ServerDataRepository serverDataRepository;
    private final ServerRepository serverRepository;


    /**
     * ServerData 엔티티를 ServerDataResponse DTO로 매핑하는 내부 메서드입니다.
     *
     * @param serverData 서버 데이터
     * @return 서버 데이터 응답 DTO
     */
    ServerDataResponse responseMapper(ServerData serverData) {
        return new ServerDataResponse(
                serverData.getServer().getServerNo(),serverData.getServer().getCompanyDomain(),
                serverData.getServerDataNo(), serverData.getServerDataLocation(),
                serverData.getServerDataGateway(), serverData.getServerDataName(),
                serverData.getMinThreshold(), serverData.getMaxThreshold(), serverData.getCreatedAt()
        );
    }


    /**
     * 서버 데이터를 등록합니다. 서버 번호와 서버 데이터 이름으로 이중 체크 했을 때 존재하면 오류 발생.
     *
     * @param serverDataRegisterRequest 등록할 서버 데이터 요청 DTO
     * @return 등록된 서버 데이터 응답 DTO
     * @throws AlreadyServerDataExistsException 이미 서버 데이터가 존재할 경우
     */
    @Override
    public ServerDataResponse registerServerData(long serverNo, ServerDataRegisterRequest serverDataRegisterRequest) {
        boolean optionalServerData = serverDataRepository.existsByServer_ServerNoAndServerDataName(serverNo, serverDataRegisterRequest.getServerDataName());
        if (optionalServerData) {
            throw new AlreadyServerDataExistsException("이미 존재하는 서버 데이터 입니다.");
        }

        Server server = serverRepository.getServerByServerNo(serverNo)
                .orElseThrow(()-> new ServerNotExistException("존재하지 않는 서버입니다!"));
        ServerData serverData = new ServerData(
                server,
                serverDataRegisterRequest.getServerDataLocation(),
                serverDataRegisterRequest.getServerDataGateway(),
                serverDataRegisterRequest.getServerDataName(),
                serverDataRegisterRequest.getMinThreshold(),
                serverDataRegisterRequest.getMaxThreshold()
        );
        ServerData savedServerData = serverDataRepository.save(serverData);
        return responseMapper(savedServerData);
    }

    /**
     * 서버 데이터 번호로 서버 데이터를 조회합니다.
     *
     * @param serverDataNo 조회할 서버 데이터 번호
     * @return 조회된 서버 데이터 응답 DTO
     * @throws ServerDataNotExistsException 데이터가 존재하지 않을 경우
     */
    @Override
    public ServerDataResponse getServerData(long serverDataNo) {
        boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if (!optionalServerData) {
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }
        ServerData serverData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        return responseMapper(serverData);
    }


    /**
     * 서버 번호로 서버 데이터를 조회합니다.
     *
     * @param serverNo 서버 번호
     * @return 조회된 서버 데이터 리스트 응답
     * @throws ServerNotExistException 서버가 존재하지 않을 경우
     */
    @Override
    public List<ServerDataResponse> getServerDataList(long serverNo) {
        boolean isExist = serverRepository.existsServerByServerNo(serverNo);
        if(!isExist) {
            throw new ServerNotExistException("존재하지 않는 서버 입니다.");
        }
        List<ServerData> serverDatas = serverDataRepository.getServerDataByServer_ServerNo(serverNo);
        List<ServerDataResponse> serverDataResponses = new ArrayList<>();
        for (ServerData serverData : serverDatas){
            ServerDataResponse serverDataResponse = responseMapper(serverData);
            serverDataResponses.add(serverDataResponse);
        }
        return serverDataResponses;
    }


    /**
     * 서버 데이터를 수정합니다.
     *
     * @param serverDataNo 수정할 서버 데이터 번호
     * @param serverDataUpdateRequest 수정할 서버 데이터 요청 DTO
     * @return 수정된 서버 데이터 응답 DTO
     * @throws ServerDataNotExistsException 데이터가 존재하지 않을 경우
     */
    @Override
    public ServerDataResponse updateServerData(long serverDataNo, ServerDataUpdateRequest serverDataUpdateRequest) {
        boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if (!optionalServerData) {
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }
        ServerData updateTargetServerData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        updateTargetServerData.update(
                serverDataUpdateRequest.getServerDataLocation(),
                serverDataUpdateRequest.getServerDataGateway(),
                serverDataUpdateRequest.getServerDataName(),
                serverDataUpdateRequest.getMinThreshold(),
                serverDataUpdateRequest.getMaxThreshold()
        );
        ServerData updatedServerData = serverDataRepository.save(updateTargetServerData);
        log.debug("updatedServerData:{}", updatedServerData);

        return responseMapper(updatedServerData);
    }

    /**
     * 서버 데이터를 삭제합니다.
     *
     * @param serverDataNo 삭제할 서버 데이터 번호
     * @return null
     * @throws ServerDataNotExistsException 데이터가 존재하지 않을 경우
     */
    @Override
    public Void delete(long serverDataNo) {
        boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if (!optionalServerData) {
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }

        ServerData deleteTargetServerData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        serverDataRepository.delete(deleteTargetServerData);
        return null;
    }
}
