package com.nhnacademy.exam.javameruleapi.server.dto;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ServerResponse {

    private final long serverNo;

    private final String companyDomain;

    private final String iphost;



    public static ServerResponse from(Server server){ //static method가 뭐지??-> 객체를 생성하지 않고 메서드 불러서 씀.
        return ServerResponse.builder()
                .serverNo(server.getServerNo())
                .companyDomain(server.getCompanyDomain())
                .iphost(server.getIphost())
                .build();
    }

    public ServerResponse(long serverNo, String companyDomain, String iphost) {
        this.serverNo = serverNo;
        this.companyDomain = companyDomain;
        this.iphost = iphost;
    }
}
