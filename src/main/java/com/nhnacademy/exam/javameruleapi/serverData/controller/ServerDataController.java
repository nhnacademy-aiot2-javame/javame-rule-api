package com.nhnacademy.exam.javameruleapi.serverData.controller;

import com.nhnacademy.exam.javameruleapi.config.annotation.HasRole;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.serverData.service.ServerDataService;
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

/**
 * 서버 데이터와 관련된 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * 서버 데이터의 등록, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/server-datas")
public class ServerDataController {

    /**
     * 서버 데이터와 관련된 비즈니스 로직을 처리하는 서비스.
     */
    private final ServerDataService serverDataService;

    /**
     *
     * @param serverDataService
     */
    public ServerDataController(ServerDataService serverDataService) {
        this.serverDataService = serverDataService;
    }

    /**
     * 서버 데이터를 등록합니다.
     *
     * @param serverDataRegisterRequest 등록할 서버 데이터 요청 DTO
     * @return 생성된 서버 데이터 응답 DTO
     * @response 201 CREATED - 서버 데이터가 성공적으로 생성됨
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping
    public ResponseEntity<ServerDataResponse> registerServerData(@Validated @RequestBody ServerDataRegisterRequest serverDataRegisterRequest) {
        ServerDataResponse serverDataResponse = serverDataService.registerServerData(serverDataRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serverDataResponse);
    }

    /**
     *  서버 데이터를 조회합니다.
     *
     * @param serverDataNo 조회할 서버 데이터 번호
     * @return 조회된 서버 데이터 응답 DTO
     * @response 200 OK - 서버 데이터 조회 성공
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER"})
    @GetMapping("/{server-data-no}")
    public ResponseEntity<ServerDataResponse> getServerData(@PathVariable("server-data-no") long serverDataNo) {
        ServerDataResponse serverDataResponse = serverDataService.getServerData(serverDataNo);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    /**
     * 서버 데이터를 수정합니다.
     *
     * @param serverDataNo 수정할 서버 데이터 번호
     * @param serverDataUpdateRequest 수정할 서버 데이터 요청 DTO
     * @return 수정된 서버 데이터 응답 DTO
     * @response 200 OK - 서버 데이터 수정 성공
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @PutMapping("/{server-data-no}")
    public ResponseEntity<ServerDataResponse> updateServerData(@PathVariable("server-data-no") long serverDataNo, @Validated @RequestBody ServerDataUpdateRequest serverDataUpdateRequest) {
        ServerDataResponse serverDataResponse = serverDataService.updateServerData(serverDataNo, serverDataUpdateRequest);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    /**
     *
     * @param serverDataNo 삭제할 서버 데이터 번호
     * @return 응답 상태 204 (No Content)
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @DeleteMapping("/{server-data-no}")
    public ResponseEntity<Void> deleteServerData(@PathVariable("server-data-no") long serverDataNo) {
        Void serverDataResponse = serverDataService.delete(serverDataNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
