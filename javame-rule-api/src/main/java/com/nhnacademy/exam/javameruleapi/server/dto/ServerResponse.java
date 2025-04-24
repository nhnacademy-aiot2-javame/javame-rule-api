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

    private final String serverId;

    private final Double cpuUsageThreshold;

    private final Double cpuTemperatureThreshold;

    private final Double memoryUsageThreshold;

    private final Double memoryTemperatureThreshold;

    private final Double diskUsageThreshold;

    private final Double diskTemperatureThreshold;

    private final String companyDomain;

    public static ServerResponse from(Server server){ //static method가 뭐지??
        return ServerResponse.builder()
                .serverNo(server.getServerNo())
                .serverId(server.getServerId())
                .cpuUsageThreshold(server.getCpuUsageThreshold())
                .cpuTemperatureThreshold(server.getCpuTemperatureThreshold())
                .memoryUsageThreshold(server.getMemoryUsageThreshold())
                .memoryTemperatureThreshold(server.getMemoryTemperatureThreshold())
                .diskUsageThreshold(server.getDiskUsageThreshold())
                .diskTemperatureThreshold(server.getDiskTemperatureThreshold())
                .build();
                    }

    public ServerResponse(long serverNo, String serverId, Double cpuUsageThreshold,
                          Double cpuTemperatureThreshold, Double memoryUsageThreshold,
                          Double memoryTemperatureThreshold, Double diskUsageThreshold,
                          Double diskTemperatureThreshold, String companyDomain) {
        this.serverNo = serverNo;
        this.serverId = serverId;
        this.cpuUsageThreshold = cpuUsageThreshold;
        this.cpuTemperatureThreshold = cpuTemperatureThreshold;
        this.memoryUsageThreshold = memoryUsageThreshold;
        this.memoryTemperatureThreshold = memoryTemperatureThreshold;
        this.diskUsageThreshold = diskUsageThreshold;
        this.diskTemperatureThreshold = diskTemperatureThreshold;
        this.companyDomain = companyDomain;
    }
}
