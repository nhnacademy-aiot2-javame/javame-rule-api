package com.nhnacademy.exam.javameruleapi.sensor.domain;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name="sensors")
@ToString
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_number", nullable = false)
    private long sensorNumber;

    @Column(name = "sensor_type", nullable = false)
    private String sensorType;

    @Column(name = "threshold", nullable = false)
    private long threshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_domain", nullable = false)
    private Company company;

    public Sensor(){

  }

    public Sensor(long sensorNumber, String sensorType, long threshold, Company company) {
        this.sensorNumber = sensorNumber;
        this.sensorType = sensorType;
        this.threshold = threshold;
        this.company = company;
    }

    public long getSensorNumber() {
        return sensorNumber;
    }

    public String getSensorType() {
        return sensorType;
    }

    public long getThreshold() {
        return threshold;
    }

    public Company getCompany() {
        return company;
    }
}
