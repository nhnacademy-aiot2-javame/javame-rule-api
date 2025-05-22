package com.nhnacademy.exam.javameruleapi.sensor.service;

import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;

import java.util.List;

/**
 * 센서 서비스 인터페이스.
 * <p>
 * 이 인터페이스는 센서 등록, 조회, 삭제와 관련된 비즈니스 로직을 정의합니다.
 */
public interface SensorService {

    /**
     * 새로운 센서를 등록합니다.
     *
     * @param sensorRegisterRequest 센서 등록에 필요한 정보가 담긴 요청 객체
     * @return 등록된 센서의 응답 데이터
     */
    SensorResponse register(SensorRegisterRequest sensorRegisterRequest);

    /**
     * 주어진 센서 번호에 해당하는 센서를 조회합니다.
     *
     * @param sensorNo 조회할 센서의 고유 번호
     * @return 조회된 센서의 응답 데이터
     */
    SensorResponse getSensor(long sensorNo);

    /**
     * 주어진 회사 도메인에 해당하는 모든 센서를 조회합니다.
     *
     * @param companyDomain 센서가 속한 회사 도메인
     * @return 조회된 센서들의 응답 데이터 리스트
     */
    List<SensorResponse> getSensors(String companyDomain);

    /**
     * 주어진 센서 번호에 해당하는 센서를 삭제합니다.
     *
     * @param sensorNo 삭제할 센서의 고유 번호
     * @return 삭제 후 응답 데이터 void
     */
    Void delete(long sensorNo);


}
