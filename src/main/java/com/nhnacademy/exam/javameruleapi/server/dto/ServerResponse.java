package com.nhnacademy.exam.javameruleapi.server.dto;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import lombok.Builder;
import lombok.Getter;


/**
 * 서버 정보를 응답할 때 사용.
 * 서버 번호, 회사 도메인, iphost  포함.
 */
@Getter
@Builder
public class ServerResponse {

    /**
     * 서버 번호 (기본 키).
     */
    private long serverNo;

    /**
     * 서버가 속한 회사의 도메인.
     */
    private String companyDomain;

    /**
     * 서버의 IP 또는 호스트명.
     */
    private String iphost;


    /**
     * Server 엔티티로부터 데이터를 추출해 ServerResponse 객체로 변환하는 정적 팩토리 메서드.
     *
     * @param server 엔티티 객체
     * @return 변환된 ServerResponse 객체
     */
    public static ServerResponse from(Server server) {
        return ServerResponse.builder()
                .serverNo(server.getServerNo())
                .companyDomain(server.getCompanyDomain())
                .iphost(server.getIphost())
                .build();
    }

    /**
     * 모든 필드를 초기화하는 생성자입니다.
     *
     * @param serverNo      서버 번호
     * @param companyDomain 회사 도메인
     * @param iphost        IP
     */
    public ServerResponse(long serverNo, String companyDomain, String iphost) {
        this.serverNo = serverNo;
        this.companyDomain = companyDomain;
        this.iphost = iphost;
    }
}
