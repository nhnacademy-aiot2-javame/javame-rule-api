package com.nhnacademy.exam.javameruleapi.server.controller;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @PostMapping
    public ResponseEntity<ServerResponse> registerServer(@Validated @RequestBody ServerRegisterRequest serverRegisterRequest){
        ServerResponse serverResponse = serverService.register(serverRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serverResponse);
    }

    @GetMapping("/{server-no}")
    public ResponseEntity<ServerResponse> getServer(@PathVariable("server-no") long serverNo){
        ServerResponse serverResponse = serverService.getServer(serverNo);
        return ResponseEntity
                .ok(serverResponse);
    }

    @GetMapping
    public ResponseEntity<List<ServerResponse>> getServers(@RequestParam("companyDomain") String companyDomain) {
        List<ServerResponse> servers = serverService.getServers(companyDomain);
        return ResponseEntity
                .ok(servers);
    }

    @PutMapping("/{server-no}")
    public ResponseEntity<ServerResponse> updateServer(@Validated @RequestBody ServerUpdateRequest serverUpdateRequest, @PathVariable("server-no") long serverNo){
        ServerResponse serverResponse = serverService.update(serverNo, serverUpdateRequest);
        return ResponseEntity
                .ok(serverResponse);
    }

    @DeleteMapping("/{server-no}")
    public ResponseEntity<Void> deleteServer(@PathVariable("server-no") long serverNo){
        serverService.delete(serverNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
