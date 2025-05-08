package com.nhnacademy.exam.javameruleapi.sensor.domain;

import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name="sensors")
@ToString
@Getter
@NoArgsConstructor
public class Sensor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_no", nullable = false)
    private long sensorNo;

    @Column(name = "company_domain")
    private String companyDomain;

    @Column(name = "sensor_id")
    private String sensorId;


    /**
     * 센서 생성하는 생성자.
     *
     * @param companyDomain
     * @param sensorId
     */
    public Sensor(String companyDomain, String sensorId) {
        this.companyDomain = companyDomain;
        this.sensorId = sensorId;
    }

}
