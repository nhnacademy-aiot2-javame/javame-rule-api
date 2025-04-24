package com.nhnacademy.exam.javameruleapi.server.dto;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ServerRegisterRequest {

    @NotNull
    private final String iphost;
    @NotNull
    private final String companyDomain;

    @NotNull
    private final String serverId;

    // 선택적 값
    private final Double cpuUsageThreshold;
    private final Double cpuTemperatureThreshold;

    private final Double memoryUsageThreshold;
    private final Double memoryTemperatureThreshold;

    private final Double diskUsageThreshold;
    private final Double diskTemperatureThreshold;

    public ServerRegisterRequest(String iphost, String companyDomain, String serverId,
                                 Double cpuUsageThreshold, Double cpuTemperatureThreshold,
                                 Double memoryUsageThreshold, Double memoryTemperatureThreshold,
                                 Double diskUsageThreshold, Double diskTemperatureThreshold) {
        this.iphost = iphost;
        this.companyDomain = companyDomain;
        this.serverId = serverId;
        this.cpuUsageThreshold = cpuUsageThreshold;
        this.cpuTemperatureThreshold = cpuTemperatureThreshold;
        this.memoryUsageThreshold = memoryUsageThreshold;
        this.memoryTemperatureThreshold = memoryTemperatureThreshold;
        this.diskUsageThreshold = diskUsageThreshold;
        this.diskTemperatureThreshold = diskTemperatureThreshold;
    }

    @Builder
    //toEntity 메서드
    public Server toEntity(){
        return Server.builder()
                .iphost(this.iphost)
                .companyDomain(this.companyDomain)
                .cpuUsageThreshold(this.cpuUsageThreshold)
                .cpuTemperatureThreshold(this.cpuTemperatureThreshold)
                .memoryUsageThreshold(this.memoryUsageThreshold)
                .memoryTemperatureThreshold(this.memoryTemperatureThreshold)
                .diskUsageThreshold(this.diskUsageThreshold)
                .diskTemperatureThreshold(this.diskTemperatureThreshold)
                .build();
    }
}
