package com.nhnacademy.exam.javameruleapi.server.domain;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "servers")
@ToString
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_number", nullable = false)
    public long serverNumber;

    @Column(name = "cpu_usage_threshold", nullable = false)
    public long cpuUsageThreshold;

    @Column(name = "cpu_temperature_threshold", nullable = false)
    public long cpuTemperatureThreshold;

    @Column(name = "memory_usage_threshold", nullable = false)
    public long memoryUsageThreshold;

    @Column(name = "memory_temperature_threshold", nullable = false)
    public long memoryTemperatureThreshold;

    @Column(name = "disk_usage_threshold", nullable = false)
    public long diskUsageThreshold;

    @Column(name = "disk_temperature_threshold", nullable = false)
    public long diskTemperatureThreshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_domain", nullable = false)
    private Company company;

    public Server(){


    }


    public Server(long serverNumber, long cpuUsageThreshold, long cpuTemperatureThreshold, long memoryUsageThreshold, long memoryTemperatureThreshold, long diskUsageThreshold, long diskTemperatureThreshold, Company company) {
        this.serverNumber = serverNumber;
        this.cpuUsageThreshold = cpuUsageThreshold;
        this.cpuTemperatureThreshold = cpuTemperatureThreshold;
        this.memoryUsageThreshold = memoryUsageThreshold;
        this.memoryTemperatureThreshold = memoryTemperatureThreshold;
        this.diskUsageThreshold = diskUsageThreshold;
        this.diskTemperatureThreshold = diskTemperatureThreshold;
        this.company = company;
    }

    public long getServerNumber() {
        return serverNumber;
    }

    public long getCpuUsageThreshold() {
        return cpuUsageThreshold;
    }

    public long getCpuTemperatureThreshold() {
        return cpuTemperatureThreshold;
    }

    public long getMemoryUsageThreshold() {
        return memoryUsageThreshold;
    }

    public long getMemoryTemperatureThreshold() {
        return memoryTemperatureThreshold;
    }

    public long getDiskUsageThreshold() {
        return diskUsageThreshold;
    }

    public long getDiskTemperatureThreshold() {
        return diskTemperatureThreshold;
    }

    public Company getCompany() {
        return company;
    }
}
