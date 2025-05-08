package com.nhnacademy.exam.javameruleapi.sensorData.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 센서 데이터 도메인 클래스.
 * 센서 ID에 대응하는 측정 항목 및 임계값 정보를 저장합니다.
 */
@Entity
@Table(name = "sensor_datas")
@ToString
@NoArgsConstructor
@Getter
public class SensorData {

    /**
     * 센서 데이터 고유 번호(PK).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_data_no", nullable = false)
    private long sensorDataNo;

    /**
     * 센서 ID (각 데이터가 어떤 센서에 속하는지 식별).
     */
    @Column(name = "sensor_id", nullable = false)
    private String sensorId;

    /**
     * 센서 데이터 이름 (예: 온도, 습도).
     */
    @Column(name = "sensor_data_name", nullable = false)
    private String sensorDataName;

    /**
     * 최소 임계값 (경고 기준).
     */
    @Column(name = "min_threshold")
    private Double minThreshold;

    /**
     * 최대 임계값 (경고 기준).
     */
    @Column(name = "max_threshold")
    private Double maxThreshold;

    /**
     * 소속 회사 도메인.
     */
    @Column(name = "company_domain", nullable = false)
    private String companyDomain;

    /**
     * SensorData 생성자.
     *
     * @param sensorId       센서 ID
     * @param sensorDataName 데이터 이름
     * @param minThreshold   최소 임계값
     * @param maxThreshold   최대 임계값
     * @param companyDomain  회사 도메인
     */
    public SensorData(String sensorId, String sensorDataName, Double minThreshold, Double maxThreshold, String companyDomain) {
        this.sensorId = sensorId;
        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.companyDomain = companyDomain;
    }

    /**
     * 센서 데이터의 이름 및 임계값 수정 메서드.
     *
     * @param sensorDataName 새로운 데이터 이름
     * @param minThreshold   새로운 최소 임계값
     * @param maxThreshold   새로운 최대 임계값
     */
    public void update(String sensorDataName, Double minThreshold, Double maxThreshold) {

        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }


}
