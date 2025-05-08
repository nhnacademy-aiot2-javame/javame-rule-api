package com.nhnacademy.exam.javameruleapi.serverData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 서버 데이터 조회 시 응답.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ServerDataResponse {

    /**
     * 서버 데이터 고유 번호.
     */
    private long serverDataNo;

    /**
     * 서버의 IP 주소 또는 호스트명입니다.
     */
    private String iphost;

    /**
     * 서버 데이터 카테고리입니다.
     *  예: "CPU", "Memory", "Disk", "Network"
     */
    private String serverDataCategory;

    /**
     * 서버 데이터 주제.
     * "CPU Usage", "Free Memory", "Disk Read Rate"
     */
    private String serverDataTopic;

    /**
     * 서버 데이터의 최소 임계값.
     * 이 값보다 낮으면 경고 조건이 될 수 있습니다
     */
    private Double minThreshold;

    /**
     * 서버 데이터의 최대 임계값입니다.
     * 이 값을 초과하면 경고 조건이 될 수 있습니다.
     */
    private Double maxThreshold;


}
