package com.nhnacademy.exam.javameruleapi.sensorData.repository;

import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA를 사용하여 SensorData 엔티티에 대한 기본 CRUD 기능과 사용자 정의 쿼리 메서드 제공.
 */
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    /**
     * 주어진 센서 데이터 이름의 센서 데이터가 존재하는지 확인.
     *
     * @param sensorDataName 센서 데이터 이름
     * @return 해당 이름의 센서 데이터가 존재하면 true, 그렇지 않으면 false
     */
    Boolean existsDataTypeBySensorDataName(String sensorDataName);

    /**
     * 센서 데이터를 저장하거나 업데이트합니다.
     *
     * @param sensorData 저장할 sensorData 객체.
     * @return 저장된 SensorData 객체
     */
    SensorData save(SensorData sensorData);

    /**
     * 센서 데이터 번호를 통해서 센서 데이터를 조회합니다.
     *
     * @param sensorDataNo 센서 데이터 번호
     * @return 조회된 SensorData 객체 (Optional)
     */
    Optional<SensorData> getSensorDataBySensorDataNo(long sensorDataNo);

    /**
     * 센서 번호를 통해 센서 데이터 리스트를 조회합니다.
     *
     * @param sensorNo 센서 번호
     * @return 조회된 SensorData (Optional)
     */
    List<SensorData> getSensorDatasBySensorNo(long sensorNo);

    /**
     * 센서 데이터를 삭제합니다.
     *
     * @param sensorData 삭제할 SensorData 객체
     */
    void delete(SensorData sensorData);

    /**
     * 주어진 센서 번호로 센서 데이터가 존재하는지 확인.
     *
     * @param sensorNo 센서 번호
     * @return 센서 데이터가 존재하면 true, 존재하지 않으면 false
     */
    Boolean existsSensorDataBySensorNo(Long sensorNo);
}
