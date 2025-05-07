package com.nhnacademy.exam.javameruleapi.serverData.service;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;

/**
 *
 */
public interface ServerDataService {

    /**
     *
     * @param serverDataRegisterRequest
     * @return
     */
    ServerDataResponse registerServerData(ServerDataRegisterRequest serverDataRegisterRequest);

    /**
     *
     * @param serverDataNo
     * @return
     */
    ServerDataResponse getServerData(long serverDataNo);

    /**
     *
     * @param serverDataUpdateRequest
     * @return
     */
    ServerDataResponse updateServerData(long serverDataNo, ServerDataUpdateRequest serverDataUpdateRequest);

    /**
     *
     * @param serverDataNo
     * @return
     */
    Void delete(long serverDataNo);
}
