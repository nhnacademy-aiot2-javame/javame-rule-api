package com.nhnacademy.exam.javameruleapi.sensorData.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "sensor_datas")
@ToString
@NoArgsConstructor
@Getter
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_data_no", nullable = false)
    private long sensorDataNo;

    @Column(name = "sensor_id", nullable = false)
    private String sensorId;

    @Column(name = "sensor_data_name", nullable = false)
    private String sensorDataName;

    @Column(name = "min_threshold")
    private Double minThreshold;

    @Column(name = "max_threshold")
    private Double maxThreshold;

    @Column(name = "company_domain", nullable = false)
    private String companyDomain;

    public SensorData(String sensorId, String sensorDataName, Double minThreshold, Double maxThreshold, String companyDomain){
        this.sensorId = sensorId;
        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.companyDomain = companyDomain;
    }

    public void update(String sensorDataName, Double minThreshold, Double maxThreshold){

        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }


}
