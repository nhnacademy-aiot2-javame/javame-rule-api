package com.nhnacademy.exam.javameruleapi.serverData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 서버 데이터 수정 요청.
 */
@AllArgsConstructor
@Getter
public class ServerDataUpdateRequest {

    /**
     * 서버 데이터 카테고리 입니다.
     * 예: "CPU", "Memory", "Disk", "Network"
     */
    private String serverDataCategory;

    /**
     * 서버 데이터 주제입니다.
     * 예: "CPU Usage", "Memory Available", "Disk IO"
     */
    private String serverDataTopic;

    /**
     * 최소 임계값.
     *  이 값보다 작을 경우 경고나 알림이 발생할 수 있습니다.
     */
    private Double minThreshold;

    /**
     * 최대 임계값.
     * 이 값을 초과할 경우 경고나 알림이 발생할 수 있습니다.
     */
    private Double maxThreshold;


}
