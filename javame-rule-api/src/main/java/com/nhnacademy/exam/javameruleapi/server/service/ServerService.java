package com.nhnacademy.exam.javameruleapi.server.service;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;

public interface ServerService {

    ServerResponse register(String serverId, ServerRegisterRequest serverRegisterRequest);

    ServerResponse update(long serverNo, ServerUpdateRequest serverUpdateRequest);

    ServerResponse getServer(long serverNo);

    void delete(long serverNo);


    }




