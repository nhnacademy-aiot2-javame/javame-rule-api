package com.nhnacademy.exam.javameruleapi.sensorData.domain;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_no")
    private Sensor sensor;

    @Column(name = "sensor_data_name", nullable = false)
    private String sensorDataName;

    @Column(name = "min_threshold")
    private Double minThreshold;

    @Column(name = "max_threshold")
    private Double maxThreshold;

    public SensorData(Sensor sensor, String sensorDataName, Double minThreshold, Double maxThreshold){
        this.sensor = sensor;
        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public void update(String sensorDataName, Double minThreshold, Double maxThreshold){

        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public void setSensor(Sensor sensor){
        this.sensor = sensor;
    }

}
