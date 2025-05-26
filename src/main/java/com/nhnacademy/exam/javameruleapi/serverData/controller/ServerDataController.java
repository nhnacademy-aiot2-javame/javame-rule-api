package com.nhnacademy.exam.javameruleapi.serverData.controller;

import com.nhnacademy.exam.javameruleapi.config.annotation.HasRole;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.serverData.service.ServerDataService;
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
 * 서버 데이터와 관련된 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * 서버 데이터의 등록, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/server-datas")
@RequiredArgsConstructor
public class ServerDataController {

    /**
     * 서버 데이터와 관련된 비즈니스 로직을 처리하는 서비스.
     */
    private final ServerDataService serverDataService;


    /**
     *
     * @param serverNo 서버 번호
     * @param serverDataRegisterRequest 서버 데이터 등록 요청
     * @return
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping
    public ResponseEntity<ServerDataResponse> registerServerData(
            long serverNo,
            @Validated @RequestBody ServerDataRegisterRequest serverDataRegisterRequest
            ) {
        ServerDataResponse serverDataResponse = serverDataService.registerServerData(serverNo, serverDataRegisterRequest);
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
    @GetMapping("/by-server-data-no/{server-data-no}")
    public ResponseEntity<ServerDataResponse> getServerData(@PathVariable("server-data-no") long serverDataNo) {
        ServerDataResponse serverDataResponse = serverDataService.getServerData(serverDataNo);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    /**
     *  서버 번호로 서버 데이터 리스트를 조회합니다.
     *
     * @param serverNo 서버 번호
     * @return 조회된 서버 데이터 리스트
     */
    @GetMapping("/by-server-no/{server-no}")
    public ResponseEntity<List<ServerDataResponse>> getServerDataList(@PathVariable("server-no") long serverNo){
        List<ServerDataResponse> serverDataResponses = serverDataService.getServerDataList(serverNo);
        return ResponseEntity
                .ok(serverDataResponses);
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
    public ResponseEntity<ServerDataResponse> updateServerData(@PathVariable("server-data-no") long serverDataNo,
                                                               @Validated @RequestBody ServerDataUpdateRequest serverDataUpdateRequest) {
        ServerDataResponse serverDataResponse = serverDataService.updateServerData(serverDataNo, serverDataUpdateRequest);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    /**
     * 서버 데이터를 삭제합니다.
     *
     * @param serverDataNo 삭제할 서버 데이터 번호
     * @return 응답 상태 204 (No Content)
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @DeleteMapping("/{server-data-no}")
    public ResponseEntity<Void> deleteServerData(@PathVariable("server-data-no") long serverDataNo) {
        serverDataService.delete(serverDataNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
