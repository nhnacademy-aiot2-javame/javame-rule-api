package com.nhnacademy.exam.javameruleapi.serverData.controller;

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

    private final ServerDataService serverDataService;


    public ServerDataController(ServerDataService serverDataService) {
        this.serverDataService = serverDataService;
    }

    @PostMapping
    public ResponseEntity<ServerDataResponse> registerServerData(@Validated @RequestBody ServerDataRegisterRequest serverDataRegisterRequest) {
        ServerDataResponse serverDataResponse = serverDataService.registerServerData(serverDataRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serverDataResponse);
    }

    @GetMapping("/{server-data-no}")
    public ResponseEntity<ServerDataResponse> getServerData(@PathVariable("server-data-no") long serverDataNo) {
        ServerDataResponse serverDataResponse = serverDataService.getServerData(serverDataNo);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    @PutMapping("/{server-data-no}")
    public ResponseEntity<ServerDataResponse> updateServerData(@PathVariable("server-data-no") long serverDataNo, @Validated @RequestBody ServerDataUpdateRequest serverDataUpdateRequest) {
        ServerDataResponse serverDataResponse = serverDataService.updateServerData(serverDataNo, serverDataUpdateRequest);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    @DeleteMapping("/{server-data-no}")
    public ResponseEntity<Void> deleteServerData(@PathVariable("server-data-no") long serverDataNo) {
        Void serverDataResponse = serverDataService.delete(serverDataNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
