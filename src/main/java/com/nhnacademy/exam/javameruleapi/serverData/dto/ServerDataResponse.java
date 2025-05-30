package com.nhnacademy.exam.javameruleapi.serverData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 서버 데이터 조회 시 응답.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ServerDataResponse {

    /**
     *  서버의 번호
     */
    private long serverNo;

    /**
     * 서버가 등록된 회사의 도메인.
     */
    private String companyDomain;

    /**
     * 서버 데이터 고유 번호.
     */
    private long serverDataNo;

    /**
     * 서버 데이터의 location
     * ex) power_meter, server_resource_data 등
     */
    private String serverDataLocation;

    /**
     * 서버 데이터의 gateway입니다.
     * ex)  modbus
     */
    private String serverDataGateway;

    /**
     * 서버 데이터의 이름입니다.
     * ex) current_amps, power_watts
     */
    private String serverDataName;


    /**
     * 최소 임계값입니다.
     * 이 값보다 낮을 경우 경고를 발생시킬 수 있습니다.
     *
     */
    private Double minThreshold; //최소 임계값

    /**
     * 최대 임계값
     * 이 값을 초과할 경우 경고를 발생시킬 수 있습니다.
     */
    private Double maxThreshold;

    /**
     *  서버데이터의 생성 시간
     */
    private LocalDateTime createdAt;

}
