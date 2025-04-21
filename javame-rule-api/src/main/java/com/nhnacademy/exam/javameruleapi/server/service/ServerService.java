package com.nhnacademy.exam.javameruleapi.server.service;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerThresholdRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerThresholdUpdateRequest;

public interface ServerService {

    ServerResponse register(ServerThresholdRegisterRequest serverThresholdRegisterRequest);

    ServerResponse getServerThreshold(long serverNo);

    ServerResponse update(ServerThresholdUpdateRequest serverThresholdUpdateRequest);

    Void delete(long serverNo);


    }




