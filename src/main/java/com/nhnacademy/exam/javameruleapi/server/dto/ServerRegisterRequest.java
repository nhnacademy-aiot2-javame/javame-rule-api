package com.nhnacademy.exam.javameruleapi.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 서버 등록 요청 DTO
 * IP 또는 호스트명 회사 도메인으로 포함합니다.
 */
@Getter
public class ServerRegisterRequest {

    /**
     * 서버의 IP 또는 호스트 명.
     * null이 아니어야 하며, 유효성 검사가 적용됨.
     */
    @NotNull
    private final String iphost;

    /**
     * 서버가 속한 회사의 도메인.
     * null이 아니어야 하며, 유효성 검사가 적용됨.
     */
    @NotNull
    private final String companyDomain;

    /**
     * 생성자. 필수 필드인 iphost와 companyDomain을 초기화합니다.
     *
     * @param iphost            서버 IP 또는 호스트명
     * @param companyDomain     회사 도메인
     */
    public ServerRegisterRequest(String iphost, String companyDomain) {
        this.iphost = iphost;
        this.companyDomain = companyDomain;
    }

}
