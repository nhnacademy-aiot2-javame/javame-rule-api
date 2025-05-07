package com.nhnacademy.exam.javameruleapi.sensor.domain;

import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import jakarta.persistence.*;
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
    private long sensorNo;

    @Column(name = "company_domain")
    private String companyDomain;

    @Column(name = "sensor_id")
    private String sensorId;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SensorData> sensorDataList = new ArrayList<>();

    /**
     * 센서만 생성하는 생성자
     *
     * @param companyDomain
     * @param sensorId
     */
    public Sensor(String companyDomain, String sensorId) {
        this.companyDomain = companyDomain;
        this.sensorId = sensorId;
    }

    public Sensor(String companyDomain, String sensorId, List<SensorData> sensorDataList) {
        this.companyDomain = companyDomain;
        this.sensorId = sensorId;
        this.sensorDataList = sensorDataList;

        // 양방향 관계를 위해 SensorData 쪽에도 this Sensor 설정
        for (SensorData data : sensorDataList){
            data.setSensor(this);
        }

    }


}
