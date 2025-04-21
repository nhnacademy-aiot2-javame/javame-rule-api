package com.nhnacademy.exam.javameruleapi.sensor.domain;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name="sensors")
@ToString
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_no", nullable = false)
    private long sensorNo;

    @Column(name = "sensor_type", nullable = false)
    private String sensorType;

    @Column(name = "threshold", nullable = false)
    private long threshold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_domain", nullable = false)
    private Company company;

    public Sensor(){

  }

    public Sensor(long sensorNo, String sensorType, long threshold, Company company) {
        this.sensorNo = sensorNo;
        this.sensorType = sensorType;
        this.threshold = threshold;
        this.company = company;
    }

    public long getSensorNo() {
        return sensorNo;
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
