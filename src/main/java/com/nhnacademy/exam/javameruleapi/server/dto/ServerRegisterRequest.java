package com.nhnacademy.exam.javameruleapi.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ServerRegisterRequest {

    @NotNull
    private final String iphost;

    @NotNull
    private final String companyDomain;

    public ServerRegisterRequest(String iphost, String companyDomain) {
        this.iphost = iphost;
        this.companyDomain = companyDomain;
    }

}
