package com.nhnacademy.exam.javameruleapi.serverData.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerDataRegisterRequest {

    /**
     * iphost
     */
    @NotNull
    private String iphost;

    /**
     * 서버 데이터 카테고리
     */
    private String serverDataCategory;

    /**
     *
     */
    private String serverDataTopic;

    /**
     * 최소 임계값
     */
    private Double minThreshold; //최소 임계값

    /**
     * 최대 임계값
     */
    private Double maxThreshold; // 최대 임계값

}
