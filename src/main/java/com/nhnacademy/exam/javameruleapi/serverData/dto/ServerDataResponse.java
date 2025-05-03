package com.nhnacademy.exam.javameruleapi.serverData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerDataResponse {

    private long serverDataNo;

    private String iphost;

    private String serverDataCategory;

    private String serverDataTopic;

    private Double minThreshold;

    private Double maxThreshold;


}
