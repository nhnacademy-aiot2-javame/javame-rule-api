package com.nhnacademy.exam.javameruleapi.server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "servers")
@ToString
@Getter
@Builder
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_no", nullable = false)
    public long serverNo;

    @Column(name = "cpu_usage_threshold")
    public Double cpuUsageThreshold; //cpu 사용량 임계치

    @Column(name = "cpu_temperature_threshold")
    public Double cpuTemperatureThreshold; //cpu 온도 임계치

    @Column(name = "memory_usage_threshold")
    public Double memoryUsageThreshold; // 메모리 사용량 임계치

    @Column(name = "memory_temperature_threshold")
    public Double memoryTemperatureThreshold; // 메모리 온도 임계치

    @Column(name = "disk_usage_threshold")
    public Double diskUsageThreshold; // 디스크 사용량 임계치

    @Column(name = "disk_temperature_threshold")
    public Double diskTemperatureThreshold; // 디스크 온도 임계치

    @Column(name = "ip_host")
    public String iphost;

    @Column(name = "company_domain")
    private String companyDomain; // db에서 찾아서

    public Server() { }

}


