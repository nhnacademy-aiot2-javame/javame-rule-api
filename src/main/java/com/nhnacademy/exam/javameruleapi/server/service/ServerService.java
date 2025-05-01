package com.nhnacademy.exam.javameruleapi.server.service;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;

import java.util.List;

public interface ServerService {

    ServerResponse register(ServerRegisterRequest serverRegisterRequest);

    List<ServerResponse> getServers(String companyDomain);

    ServerResponse update(long serverNo, ServerUpdateRequest serverUpdateRequest);

    ServerResponse getServer(long serverNo);

    Void delete(long serverNo);

}




