package com.nhnacademy.exam.javameruleapi.server.dto;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ServerRegisterRequest {

    @NotNull
    public String iphost;
    @NotNull
    public String companyDomain;

    // 선택적 값
    public Double cpuUsageThreshold;
    public Double cpuTemperatureThreshold;

    public Double memoryUsageThreshold;
    public Double memoryTemperatureThreshold;

    public Double diskUsageThreshold;
    public Double diskTemperatureThreshold;

    @Builder
    public ServerRegisterRequest(String iphost, String companyDomain,
                                 Double cpuUsageThreshold, Double cpuTemperatureThreshold,
                                 Double memoryUsageThreshold, Double memoryTemperatureThreshold,
                                 Double diskUsageThreshold, Double diskTemperatureThreshold){
        this.iphost = iphost;
        this.companyDomain = companyDomain;
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
                .cpuUsageThreshold(this.cpuUsageThreshold)
                .cpuTemperatureThreshold(this.cpuTemperatureThreshold)
                .memoryUsageThreshold(this.memoryUsageThreshold)
                .memoryTemperatureThreshold(this.memoryTemperatureThreshold)
                .diskUsageThreshold(this.diskUsageThreshold)
                .diskTemperatureThreshold(this.diskTemperatureThreshold)
                .build();
    }
}
