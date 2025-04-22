package com.nhnacademy.exam.javameruleapi.server.dto;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ServerUpdateRequest {

    public String iphost;

    public String companyDomain; // 필수값

    public String serverId;

    public Double cpuTemperatureThreshold;
    public Double cpuUsageThreshold;

    public Double memoryUsageThreshold;
    public Double memoryTemperatureThreshold;

    public Double diskUsageThreshold;
    public Double diskTemperatureThreshold;

    @Builder
    public ServerUpdateRequest(String iphost, String companyDomain, String serverId,
                                 Double cpuUsageThreshold, Double cpuTemperatureThreshold,
                                 Double memoryUsageThreshold, Double memoryTemperatureThreshold,
                                 Double diskUsageThreshold, Double diskTemperatureThreshold){
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

    //toEntity 메서드
    public Server toEntity(){
        return Server.builder()
                .iphost(this.iphost)
                .companyDomain(this.companyDomain)
                .serverId(this.serverId)
                .cpuUsageThreshold(this.cpuUsageThreshold)
                .cpuTemperatureThreshold(this.cpuTemperatureThreshold)
                .memoryUsageThreshold(this.memoryUsageThreshold)
                .memoryTemperatureThreshold(this.memoryTemperatureThreshold)
                .diskUsageThreshold(this.diskUsageThreshold)
                .diskTemperatureThreshold(this.diskTemperatureThreshold)
                .build();
    }
}
