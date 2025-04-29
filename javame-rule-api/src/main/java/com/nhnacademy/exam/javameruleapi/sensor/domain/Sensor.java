package com.nhnacademy.exam.javameruleapi.sensor.domain;

import com.nhnacademy.exam.javameruleapi.dataType.domain.DataType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="sensors")
@ToString
@Getter
@NoArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_no", nullable = false)
    private Long sensorNo;

    @Column(name = "company_domain")
    private String companyDomain;

    @Column(name = "sensor_id")
    private String sensorId;


    public Sensor(String companyDomain, String sensorId) {
        this.companyDomain = companyDomain;
        this.sensorId = sensorId;
    }
}
