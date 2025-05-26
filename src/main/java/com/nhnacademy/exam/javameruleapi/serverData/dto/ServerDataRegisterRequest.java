package com.nhnacademy.exam.javameruleapi.serverData.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 서버 데이터를 등록할 때 사용하는 요청 dto.
 */
@AllArgsConstructor
@Getter
public class ServerDataRegisterRequest {

    /**
     *  서버의 IP 주소.
     */
    @NotNull
    private long serverNo;

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



}
