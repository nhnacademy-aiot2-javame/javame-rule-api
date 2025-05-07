package com.nhnacademy.exam.javameruleapi.serverData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerDataUpdateRequest {

    private String serverDataCategory;

    private String serverDataTopic;

    private Double minThreshold;

    private Double maxThreshold;


}
