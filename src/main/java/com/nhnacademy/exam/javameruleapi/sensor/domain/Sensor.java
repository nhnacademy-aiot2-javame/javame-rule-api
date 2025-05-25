package com.nhnacademy.exam.javameruleapi.sensor.domain;


import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 센서 클래스.
 */
@Entity
@Table(name = "sensors")
@ToString
@Getter
@NoArgsConstructor
public class Sensor {


    /**
     * 센서 번호.
     * 이 값은 데이터베이스에서 자동으로 생성되는 고유 ID 입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_no", nullable = false, unique = true)
    private long sensorNo;

    /**
     * 센서에 연결된 센서 데이터 목록
     * <p>
     * Sensor와 SensorData 간의 일대다(1:N) 양방향 관계에서
     * 'Sensor'는 하나이고, 해당 센서에 연결된 여러 개의 SensorData가 존재합니다.
     * <p>
     * 이 리스트는 SensorData 앤티티의 'sensor'필드를 통해 매핑되며,
     * cascade = CascadeType.ALL 옵션으로 인해 센서가 저장/삭제될 때
     * 관련된 센서 데이터들도 함께 저장/삭제됩니다.
     * <p>
     * orphanRemoval = true는 이 센서에서 센서 데이터가 제거되면,
     * 해당 센서 데이터가 DB에서도 자동으로 삭제되도록 합니다.
     */
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SensorData> sensorDataList = new ArrayList<>();

    /**
     * 회사 도메인.
     * 센서가 속한 회사의 도메인 이름을 저장합니다.
     */
    @Column(name = "company_domain")
    private String companyDomain;

    /**
     * 센서 ID.
     * 센서 ID를 저장합니다.
     */
    @Column(name = "sensor_id")
    private String sensorId;

    /**
     * 생성일자
     * 센서의 생성 일자를 저장합니다.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 엔티티가 영속 상태가 되기 직전에 호출되는 롤백 메서드입니다.
     * <p>
     * 이 메서드는 JPA에서 엔티티를 DB에 저장(insert)하기 전에 자동으로 실행됩니다.
     * 여기서는 센서의 생성 시점을 기록하기 위해 created_at 필드에 현재 시간을 저장합니다.
     * 이 메서드를 통해 생성일자가 명시적으로 주어지지 않아도 자동으로 저장되도록 보장할 수 있음.
     */
    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 센서 데이터 항목을 추가하고, 해당 데이터와의 연관 관계를 설정.
     * <p>
     * 이 메서드는 센서에 새로운 SensorData를 추가라 때 사용됨.
     * 내부 리스트에 데이터를 추가한 뒤, 센서 데이터의 sensor 필드에
     * 현재 센서 객체를 설정하여 양방향 연관관계를 유지합니다.
     *
     * @param sensorData
     */
    public void addSensorData(SensorData sensorData) {
        sensorDataList.add(sensorData);
        sensorData.setSensor(this);
    }

    /**
     * 센서 생성하는 생성자입니다.
     *
     * @param companyDomain
     * @param sensorId
     */
    public Sensor(String companyDomain, String sensorId) {
        this.companyDomain = companyDomain;
        this.sensorId = sensorId;
    }

}
