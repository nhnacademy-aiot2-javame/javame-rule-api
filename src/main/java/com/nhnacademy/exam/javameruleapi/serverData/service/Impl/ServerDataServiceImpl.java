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


/**
 * ServerDataService 인터페이스의 구현체로, 서버 데이터에 대한 등록, 조회, 수정, 삭제 비즈니스 로직을 담당합니다.
 */
@Slf4j
@Service
@Transactional
public class ServerDataServiceImpl implements ServerDataService {

    /**
     * 서버 데이터에 대한 CRUD 작업을 수행하는 JPA 레포지토리.
     */
    private final ServerDataRepository serverDataRepository;

    /**
     * 생성자 주입 방식으로 Repository를 주입받습니다.
     *
     * @param serverDataRepository 서버 데이터 레포지토리
     */
    public ServerDataServiceImpl(ServerDataRepository serverDataRepository) {
        this.serverDataRepository = serverDataRepository;
    }

    /**
     * ServerData 엔티티를 ServerDataResponse DTO로 매핑하는 내부 메서드입니다.
     *
     * @param serverData 서버 데이터
     * @return 서버 데이터 응답 DTO
     */
    ServerDataResponse responseMapper(ServerData serverData) {
        return new ServerDataResponse(
                serverData.getServerDataNo(), serverData.getIphost(),
                serverData.getServerDataCategory(), serverData.getServerDataTopic(),
                serverData.getMinThreshold(), serverData.getMaxThreshold()
        );
    }


    /**
     * 서버 데이터를 등록합니다. 이미 동일 iphost의 데이터가 존재하면 예외를 발생시킵니다.
     *
     * @param serverDataRegisterRequest 등록할 서버 데이터 요청 DTO
     * @return 등록된 서버 데이터 응답 DTO
     * @throws AlreadyServerDataExistsException 이미 서버 데이터가 존재할 경우
     */
    @Override
    public ServerDataResponse registerServerData(ServerDataRegisterRequest serverDataRegisterRequest) {
        Boolean optionalServerData = serverDataRepository.existsServerDataByIphost(serverDataRegisterRequest.getIphost());
        if (optionalServerData) {
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

    /**
     * 서버 데이터 번호로 서버 데이터를 조회합니다.
     *
     * @param serverDataNo 조회할 서버 데이터 번호
     * @return 조회된 서버 데이터 응답 DTO
     * @throws ServerDataNotExistsException 데이터가 존재하지 않을 경우
     */
    @Override
    public ServerDataResponse getServerData(long serverDataNo) {
        Boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if (!optionalServerData) {
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }
        ServerData serverData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        return responseMapper(serverData);
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
        Boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if (!optionalServerData) {
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

    /**
     * 서버 데이터를 삭제합니다.
     *
     * @param serverDataNo 삭제할 서버 데이터 번호
     * @return null
     * @throws ServerDataNotExistsException 데이터가 존재하지 않을 경우
     */
    @Override
    public Void delete(long serverDataNo) {
        Boolean optionalServerData = serverDataRepository.existsServerDataByServerDataNo(serverDataNo);
        if (!optionalServerData) {
            throw new ServerDataNotExistsException("서버 데이터가 존재하지 않습니다.");
        }

        ServerData deleteTargetServerData = serverDataRepository.getServerDataByServerDataNo(serverDataNo);
        serverDataRepository.delete(deleteTargetServerData);
        return null;
    }
}
