package com.nhnacademy.exam.javameruleapi.sensorData.service;


import com.nhnacademy.javame.common.dto.sensorData.SensorDataRegisterRequest;
import com.nhnacademy.javame.common.dto.sensorData.SensorDataResponse;
import com.nhnacademy.javame.common.dto.sensorData.SensorDataUpdateRequest;

import java.util.List;

/**
 * 센서 데이터에 대한 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 */
public interface SensorDataService {

    /**
     * 센서 데이터를 등록합니다.
     *
     * @param sensorNo                  센서
     * @param sensorDataRegisterRequest 등록할 센서 데이터 요청
     * @return 등록된 센서 데이터 응답.
     */
    SensorDataResponse register(long sensorNo, SensorDataRegisterRequest sensorDataRegisterRequest);


    /**
     * 센서 데이터 번호를 이용해 센서 데이터를 조회합니다.
     *
     * @param sensorDataNo 센서 데이터 번호
     * @return 센서 데이터 응답
     */
    SensorDataResponse getSensorDataBySensorDataNo(long sensorDataNo);


    /**
     * 센서 번호를 이용해 센서 데이터 리스트를 조회합니다.
     *
     * @param sensorNo 센서 번호
     * @return 센서 데이터 응답 리스트
     */
    List<SensorDataResponse> getSensorDatasBySensorNo(Long sensorNo);

    /**
     * 센서 데이터를 수정합니다.
     *
     * @param dataTypeNo              수정할 센서 데이터 번호
     * @param sensorDataUpdateRequest 수정 요청 DTO
     * @return 수정된 센서 데이터 응답
     */
    SensorDataResponse update(long dataTypeNo, SensorDataUpdateRequest sensorDataUpdateRequest);

    /**
     * 센서 데이터를 삭제합니다.
     *
     * @param dataTypeNo
     * @return 반환값 없음.
     */
    Void delete(long dataTypeNo);
}
