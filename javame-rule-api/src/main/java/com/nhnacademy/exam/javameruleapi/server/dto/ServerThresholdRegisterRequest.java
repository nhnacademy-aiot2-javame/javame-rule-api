package com.nhnacademy.exam.javameruleapi.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerThresholdRegisterRequest {

    private long serverNo;

    public Double cpuUsageThreshold;
    public Double cpuTemperatureThreshold;

    public Double memoryUsageThreshold;
    public Double memoryTemperatureThreshold;

    public Double diskUsageThreshold;
    public Double diskTemperatureThreshold;

    public String iphost;
    public String companyDomain;


}
