package com.nhnacademy.exam.javameruleapi.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ServerThresholdUpdateRequest {

    public Double cpuUsageThreshold;
    public Double cpuTemperatureThreshold;

    public Double memoryUsageThreshold;
    public Double memoryTemperatureThreshold;

    public Double diskUsageThreshold;
    public Double diskTemperatureThreshold;

    public String iphost;

    @NotNull
    public String companyDomain; // 필수값

}
