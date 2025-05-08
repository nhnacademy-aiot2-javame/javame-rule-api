package com.nhnacademy.exam.javameruleapi.serverData.service;

import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;

/**
 * 서버 데이터에 대한 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface ServerDataService {

    /**
     * 새로운 서버 데이터를 등록합니다.
     *
     * @param serverDataRegisterRequest 등록할 서버 데이터 요청 DTO
     * @return 등록된 서버 데이터 응답 DTO
     */
    ServerDataResponse registerServerData(ServerDataRegisterRequest serverDataRegisterRequest);

    /**
     * 서버 데이터 번호를 통해 서버 데이터를 조회합니다.
     *
     * @param serverDataNo 조회할 서버 데이터 번호
     * @return 조회된 서버 데이터 응답 DTO
     */
    ServerDataResponse getServerData(long serverDataNo);

    /**
     * 서버 데이터를 수정합니다.
     *
     * @param serverDataNo 수정할 서버 데이터 번호
     * @param serverDataUpdateRequest 수정할 서버 데이터 요청 DTO
     * @return 수정된 서버 데이터 응답 DTO
     */
    ServerDataResponse updateServerData(long serverDataNo, ServerDataUpdateRequest serverDataUpdateRequest);

    /**
     * 서버 데이터를 삭제합니다.
     *
     * @param serverDataNo 삭제할 서버 데이터 번호
     * @return 반환값 없음
     */
    Void delete(long serverDataNo);
}
