package com.nhnacademy.exam.javameruleapi.server.controller;

import com.nhnacademy.exam.javameruleapi.config.annotation.HasRole;
import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 서버 관련 API 요청을 처리하는 컨트롤러 입니다.
 * 서버등록, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/rule/servers")
@RequiredArgsConstructor
public class ServerController {

    /**
     * 서버 비즈니스 로직을 처리하는 서비스 클래스.
     */
    private final ServerService serverService;


    /**
     * 새로운 서버 정보를 등록합니다.
     *
     * @param serverRegisterRequest 서버 등록 요청 dto
     * @return 등록된 서버 정보 응답. HTTP Status 201(CREATED)
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping
    public ResponseEntity<ServerResponse> registerServer(@Validated @RequestBody ServerRegisterRequest serverRegisterRequest) {
        ServerResponse serverResponse = serverService.register(serverRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serverResponse);
    }

    /**
     * 서버 번호로 서버 정보를 조회합니다.
     *
     * @param serverNo 서버 번호
     * @return 서버 정보 응답. HTTP Status 200(OK).
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER"})
    @GetMapping("/no/{server-no}")
    public ResponseEntity<ServerResponse> getServer(@PathVariable("server-no") long serverNo) {
        ServerResponse serverResponse = serverService.getServer(serverNo);
        return ResponseEntity
                .ok(serverResponse);
    }


    /**
     * 서버 Iphost를 기반으로 서버정보를 조회합니다.
     * @param iphost iphost
     * @return 서버 정보
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER"})
    @GetMapping("/{company-domain}/iphost/{iphost}")
    public ResponseEntity<ServerResponse> getServerByIphost(@PathVariable("iphost") String iphost, @PathVariable("company-domain") String companyDomain){
        ServerResponse serverResponse = serverService.getServerResponseByIphost(companyDomain, iphost);
        return ResponseEntity.ok(serverResponse);
    }


    /**
     * 회사 도메인으로 서버 정보 리스트를 조회합니다.
     *
     * @param companyDomain 회사 도메인
     * @return 조회된 서버 정보 리스트 응답. HTTP Status 200(OK).
     */
//    @HasRole({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER"})
    @GetMapping("/cp/{companyDomain}")
    public ResponseEntity<List<ServerResponse>> getServers(@PathVariable("companyDomain") String companyDomain) {
        List<ServerResponse> servers = serverService.getServers(companyDomain);
        return ResponseEntity
                .ok(servers);
    }

    /**
     *  서버 정보 업데이트.
     *
     * @param serverUpdateRequest 서버 업데이트 요청
     * @param serverNo 서버 번호
     * @return 업데이트 된 서버 정보 응답. HTTP Status 200(OK).
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @PutMapping("/{server-no}")
    public ResponseEntity<ServerResponse> updateServer(@Validated @RequestBody ServerUpdateRequest serverUpdateRequest, @PathVariable("server-no") long serverNo) {
        ServerResponse serverResponse = serverService.update(serverNo, serverUpdateRequest);
        return ResponseEntity
                .ok(serverResponse);
    }

    /**
     * 서버 정보 삭제.
     *
     * @param serverNo 서버 번호
     * @return 삭제된 서버 정보 응답. HTTP Status 204(No Content)
     */
    @HasRole({"ROLE_ADMIN", "ROLE_OWNER"})
    @DeleteMapping("/{server-no}")
    public ResponseEntity<Void> deleteServer(@PathVariable("server-no") long serverNo) {
        serverService.delete(serverNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
