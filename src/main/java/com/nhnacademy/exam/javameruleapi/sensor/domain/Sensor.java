package com.nhnacademy.exam.javameruleapi.sensor.domain;



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
     * 회사 도메인.
     * 센서가 속한 회사의 도메인 이름을 저장합니다.
     */
    @Column(name = "company_domain")
    private String companyDomain;

    /**
     * 센서 ID.
     * 센서를 고유하게 식별할 수 있는 ID를 저장합니다.
     */
    @Column(name = "sensor_id", unique = true)
    private String sensorId;


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
