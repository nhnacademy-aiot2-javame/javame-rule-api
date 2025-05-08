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
    private String iphost;

    /**
     * 서버 데이터의 카테고리 입니다.
     * ex) "CPU", "Memory", "Disk", "Network"
     */
    private String serverDataCategory;

    /**
     * 서버 데이터의 세부 주제입니다.
     * ex)  "CPU Usage", "Available Memory", "Disk I/O", "Network Throughput"
     */
    private String serverDataTopic;

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
     *  서버가 속한 회사 도메인입니다.
     */
    private String companyDomain;

}
