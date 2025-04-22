package com.nhnacademy.exam.javameruleapi.server.dto;

import com.nhnacademy.exam.javameruleapi.server.dto.memory.MemoryThreshold;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerThresholdRegisterRequest {

    private long serverNo;

    public Double cpuUsageThreshold;
    public Double cpuTemperatureThreshold;

    public MemoryThreshold memoryThreshold;

//    public Double memoryUsageThreshold;
//    public Double memoryTemperatureThreshold;

    public Double diskUsageThreshold;
    public Double diskTemperatureThreshold;

    public String iphost;
    public String companyDomain;



    // 파라미터 8개 한번에 꼭 입력하는가...


}
