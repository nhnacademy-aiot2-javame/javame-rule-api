package com.nhnacademy.exam.javameruleapi.sensor.controller;

import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  센서에 대한 HTTP 요청을 처리하는 컨트롤러입니다.
 *  이 클래스는 센서의 등록, 조회, 삭제와 관련된 API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/sensors")
public class SensorController {

    /**
     * 센서 관련 비즈니스 로직 처리하는 서비스.
     */
    private final SensorService sensorService;


    /**
     * SensorController의 생성자.
     *
     * @param sensorService 센서 서비스 객체
     */
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     *  새로운 센서를 등록합니다.
     *
     * @param sensorRegisterRequest 센서 등록 요청 객체
     * @return 셍성된 센서 정보와 함께 HTTP 201(CREATED) 상태 반환
     */
    @PostMapping
    ResponseEntity<SensorResponse> registerSensor(@Validated @RequestBody SensorRegisterRequest sensorRegisterRequest) {
        SensorResponse sensorResponse = sensorService.register(sensorRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sensorResponse);
    }

    /**
     *  특정 센서 정보를 조회.
     *
     * @param sensorNo 센서 번호
     * @return 센서 응답 객체와 함께 HTTP 200(OK) 반환
     */
    @GetMapping("/{sensor-id}")
    ResponseEntity<SensorResponse> getSensor(@PathVariable("sensor-id") long sensorNo) {
        SensorResponse sensorResponse = sensorService.getSensor(sensorNo);
        return ResponseEntity.ok(sensorResponse);
    }

    /**
     *  특정 회사 도메인에 등록된 모든 센서 목록을 조회.
     *
     * @param companyDomain 회사 도메인
     * @return 센서 응답 객체 리스트와 함께 HTTP 200(OK) 반환
     */
    @GetMapping
    ResponseEntity<List<SensorResponse>> getSensors(@RequestParam String companyDomain) {
        List<SensorResponse> sensorResponses = sensorService.getSensors(companyDomain);
        return ResponseEntity.ok(sensorResponses);
    }


    /**
     * 특정 센서를 삭제합니다.
     *
     * @param sensorNo 센서 번호
     * @return HTTP 204(No Content) 상태 반환
     */
    @DeleteMapping("/{sensor-id}")
    ResponseEntity<Void> deleteSensor(@PathVariable("sensor-id") long sensorNo) {
        sensorService.delete(sensorNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



}
