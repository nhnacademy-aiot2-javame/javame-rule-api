package com.nhnacademy.exam.javameruleapi.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class ServerResponse {

    private final long serverNo;

    private final Double cpuUsageThreshold;

    private final Double cpuTemperatureThreshold;

    private final Double memoryUsageThreshold;

    private final Double memoryTemperatureThreshold;

    private final Double diskUsageThreshold;

    private final long diskTemperatureThreshold;

    private final String;




}
