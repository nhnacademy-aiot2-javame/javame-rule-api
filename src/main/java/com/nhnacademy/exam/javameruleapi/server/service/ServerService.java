package com.nhnacademy.exam.javameruleapi.server.service;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;

import java.util.List;

/**
 *  서버 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface ServerService {

    /**
     * 서버를 등록합니다.
     *
     * @param serverRegisterRequest 등록 요청 정보(IP, companyDomain)
     * @return 등록된 서버 응답.
     */
    ServerResponse register(ServerRegisterRequest serverRegisterRequest);

    /**
     * 서버 번호를 기반으로 서버 정보를 조회.
     *
     * @param serverNo 조회할 서버의 고유 번호
     * @return 서버 정보
     */
    ServerResponse getServer(long serverNo);

    /**
     * Iphost를 기반으로 서버 정보 조회.
     * @param companyDomain 회사 도메인
     * @param iphost iphost
     * @return 서버 정보
     */
    ServerResponse getServerResponseByIphost(String companyDomain, String iphost);


    /**
     *  특정 회사 도메인에 속한 모든 서버를 조회합니다.
     *
     * @param companyDomain 조회할 회사 도메인
     * @return 해당 도메인에 속한 서버들의 리스트.
     */
    List<ServerResponse> getServers(String companyDomain);

    /**
     * 서버의 IP 주소를 수정.
     *
     * @param serverNo 수정할 서버의 고유 번호
     * @param serverUpdateRequest 서버 수정 요청 (IP 주소)
     * @return 수정된 서버 응답.
     */
    ServerResponse update(long serverNo, ServerUpdateRequest serverUpdateRequest);


    /**
     * 서버를 삭제합니다.
     *
     * @param serverNo 삭제할 서버의 고유 번호
     * @return null
     */
    Void delete(long serverNo);

}




