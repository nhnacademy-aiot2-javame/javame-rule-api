package com.nhnacademy.exam.javameruleapi.sensorData.controller;

import com.nhnacademy.exam.javameruleapi.config.annotation.HasRole;

import com.nhnacademy.exam.javameruleapi.sensorData.service.SensorDataService;
import com.nhnacademy.javame.common.dto.sensorData.SensorDataRegisterRequest;
import com.nhnacademy.javame.common.dto.sensorData.SensorDataResponse;
import com.nhnacademy.javame.common.dto.sensorData.SensorDataUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 센서 데이터 등록, 조회, 수정, 삭제를 위한 REST 컨트롤러.
 */
@RestController
@RequestMapping("/rule/sensor-datas")
@RequiredArgsConstructor
public class SensorDataController {

    /**
     * 센서 데이터 관련 비즈니스 로직을 처리하는 서비스.
     * <p>
     * 이 서비스는 센서 데이터의 등록, 조회, 수정, 삭제 기능을 제공.
     */
    private final SensorDataService sensorDataService;




    /**
     * 센서 번호로 센서 데이터 리스트를 조회 합니다.
     *
     * @param sensorNo 센서 번호
     * @return 생성된 센서 데이터 응답 리스트. 코드는 200(OK)
     */
//    @HasRole({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER"})
    @GetMapping("/by-sensor-no/{sensor-no}")
    ResponseEntity<List<SensorDataResponse>> getSensorDatasBySensorNo(@PathVariable("sensor-no") long sensorNo) {
        List<SensorDataResponse> sensorDataResponses = sensorDataService.getSensorDatasBySensorNo(sensorNo);
        return ResponseEntity
                .ok(sensorDataResponses);
    }

    /**
     * 센서 데이터 등록.
     *
     * @param sensorNo                  센서번호
     * @param sensorDataRegisterRequest 센서 데이터 등록 요청
     * @return 생성된 센서 데이터 응답.HTTP 코드는 201(CREATED)
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/{sensor-no}")
    ResponseEntity<SensorDataResponse> registerSensorData(
            @PathVariable("sensor-no") long sensorNo,
            @Validated @RequestBody SensorDataRegisterRequest sensorDataRegisterRequest) {
        SensorDataResponse sensorDataResponse = sensorDataService.register(sensorNo, sensorDataRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sensorDataResponse);
    }


    /**
     * 센서 데이터 번호로 조회.
     *
     * @param sensorDataNo 센서 데이터 고유 번호.
     * @return 센서 데이터 응답. HTTP 코드는 200(OK)
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER"})
    @GetMapping("/by-dt-no/{sensor-data-no}")
    ResponseEntity<SensorDataResponse> getSensorDataBySensorDataNo(@PathVariable("sensor-data-no") long sensorDataNo) {
        SensorDataResponse sensorDataResponse = sensorDataService.getSensorDataBySensorDataNo(sensorDataNo);
        return ResponseEntity
                .ok(sensorDataResponse);
    }



    /**
     * 센서 데이터 수정.
     *
     * @param sensorDataNo            수정할 센서 데이터 번호
     * @param sensorDataUpdateRequest 수정 요청 데이터
     * @return 수정된 센서 데이터 응답. HTTP 코드는 200(OK)
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @PutMapping("/{sensor-data-no}")
    ResponseEntity<SensorDataResponse> updateSensorData(@PathVariable("sensor-data-no") long sensorDataNo, @Validated @RequestBody SensorDataUpdateRequest sensorDataUpdateRequest) {
        SensorDataResponse sensorDataResponse = sensorDataService.update(sensorDataNo, sensorDataUpdateRequest);
        return ResponseEntity
                .ok(sensorDataResponse);
    }

    /**
     * 센서 데이터 삭제 엔드포인트.
     *
     * @param sensorDataNo 삭제할 센서 데이터 번호.
     * @return 204(No Content) 응답.
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @DeleteMapping("/{sensor-data-no}")
    ResponseEntity<Void> deleteDataType(@PathVariable("sensor-data-no") long sensorDataNo) {
        sensorDataService.delete(sensorDataNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
