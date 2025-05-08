package com.nhnacademy.exam.javameruleapi.sensor.repository;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA를 이용하여 센서 정보를 DB에서 조회하거나 저장, 삭제 등의 작업을 수행.
 */
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    /**
     * 센서 번호를 기반으로 센서를 조회합니다.
     *
     * @param sensorNo 센서의 고유 번호
     * @return 조회된 센서 객체(Optional)
     */
    Optional<Sensor> getSensorBySensorNo(long sensorNo);

    /**
     * 센서 ID를 기반으로 센서를 조회.
     *
     * @param sensorId 센서의 고유 ID
     * @return 조회된 센서 객체(Optional)
     */
    Optional<Sensor> getSensorBySensorId(String sensorId);

    /**
     * 회사 도메인을 기반으로 해당 회사의 모든 센서를 조회합니다.
     *
     * @param companyDomain 회사의 도메인
     * @return 조회된 센서 리스트 (Optional)
     */
    Optional<List<Sensor>> getSensorsByCompanyDomain(String companyDomain);

    /**
     * 주어진 sensorId와 companyDomain 조합으로 센서가 존재하는지 확인합니다.
     *
     * @param sensorId 센서의 고유 ID
     * @param companyDomain 회사의 도메인
     * @return 센서가 존재하면 true, 그렇지 않으면 false
     */
    boolean existsSensorBySensorIdAndCompanyDomain(String sensorId, String companyDomain);
}
