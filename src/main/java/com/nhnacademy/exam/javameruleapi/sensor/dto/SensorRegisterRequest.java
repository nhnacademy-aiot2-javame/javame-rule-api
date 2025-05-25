package com.nhnacademy.exam.javameruleapi.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 센서 등록 요청을 위한 DTO 클래스입니다.
 * <p>
 * 클라이언트로부터 센서 등록에 필요한 정보를 전달받는 데 사용됩니다.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SensorRegisterRequest {

    /**
     * 센서가 속한 회사의 도메인.
     */
    private String companyDomain;

    /**
     * 센서의 고유 ID.
     */
    private String sensorId;


}
