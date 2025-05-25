package com.nhnacademy.exam.javameruleapi.sensorData.domain;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

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
     * 센서와의 다대일 관계를 나타냅니다.
     * <p>
     * 이 필드는 각 센서 데이터가 어느 센서에 속해 있는지를 명시합니다.
     * 실제 데이터베이스에서는 이 필드가 외래키(FK) 역할을 하며,
     * 'sensor_no' 컬럼을 통해 Sensor 테이블과 조인됩니다.
     * <p>
     * 즉, 여러 SensorData가 하나의 Sensor에 연결되는 구조임.
     */
    @ManyToOne
    @JoinColumn(name = "sensor_no")
    private Sensor sensor;


    /**
     * 센서 데이터 이름 (예: data, test 등).
     */
    @Column(name = "sensor_data_name", nullable = false)
    private String sensorDataName;

    /**
     * 센서 데이터 location (예 : power_meter, server_resources_data)
     */
    @Column(name = "sensor_data_location")
    private String sensorDataLocation;


    /**
     * 센서 데이터 게이트웨이 (예 : cpu, disk 등등)
     */
    @Column(name = "sensor_data_gateway")
    private String sensorDataGateway;


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
     * 생성일자
     * 센서의 생성 일자를 저장합니다.
     */
    @Column(name = "created_at")
    private LocalDateTime created_at;

    /**
     * 엔티티가 영속 상태가 되기 직전에 호출되는 롤백 메서드입니다.
     * <p>
     * 이 메서드는 JPA에서 엔티티를 DB에 저장(insert)하기 전에 자동으로 실행됩니다.
     * 여기서는 센서의 생성 시점을 기록하기 위해 created_at 필드에 현재 시간을 저장합니다.
     * 이 메서드를 통해 생성일자가 명시적으로 주어지지 않아도 자동으로 저장되도록 보장할 수 있음.
     */
    @PrePersist
    protected void prePersist() {
        this.created_at = LocalDateTime.now();
    }

    /**
     * 센서 정보를 설정합니다.
     * <p>
     * 이 메서드는 현재 객체에 연관된 엔티티를 주입하거나 변경할 때 사용됨.
     *
     * @param sensor 설정할 센서 객체
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    /**
     * SensorData 생성자.
     *
     * @param sensor             센서
     * @param sensorDataGateway  센서데이터 게이트웨이
     * @param sensorDataLocation 센서데이터 위치
     * @param sensorDataName     센서데이터 이름
     * @param minThreshold       최소 임계값
     * @param maxThreshold       최대 임계값
     */
    public SensorData(Sensor sensor, String sensorDataGateway, String sensorDataLocation, String sensorDataName, Double minThreshold, Double maxThreshold) {
        this.sensor = sensor;
        this.sensorDataGateway = sensorDataGateway;
        this.sensorDataLocation = sensorDataLocation;
        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;

    }

    /**
     * 센서 데이터의 이름 및 임계값 수정 메서드.
     *
     * @param sensorDataLocation 센서 데이터 위치
     * @param sensorDataGateway  센서 데이터 게이트웨이
     * @param sensorDataName     새로운 데이터 이름
     * @param minThreshold       새로운 최소 임계값
     * @param maxThreshold       새로운 최대 임계값
     */
    public void update(String sensorDataLocation, String sensorDataGateway, String  sensorDataName, Double minThreshold, Double maxThreshold) {
        this.sensorDataLocation = sensorDataLocation;
        this.sensorDataGateway = sensorDataGateway;
        this.sensorDataName = sensorDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }


}
