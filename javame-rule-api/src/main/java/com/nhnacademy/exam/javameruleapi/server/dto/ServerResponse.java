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

    public static ServerResponse from(Server server){
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

}
