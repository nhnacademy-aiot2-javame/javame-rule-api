package com.nhnacademy.exam.javameruleapi.sensor.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 센서 응답 정보를 담습니다.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SensorResponse {

    /**
     * 센서의 고유 식별 번호.
     */
    private long sensorNo;

    /**
     * 센서가 속한 회사의 도메인.
     */
    private String companyDomain;

    /**
     * 센서의 고유 ID.
     */
    private String sensorId;

    /**
     * 센서의 생성일자.
     */
    private LocalDateTime createdAt;


}
