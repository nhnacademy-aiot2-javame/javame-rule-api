package com.nhnacademy.exam.javameruleapi.server.controller;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerUpdateRequest;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @PostMapping
    public ResponseEntity<ServerResponse> registerServerThreshold(@Validated @RequestBody ServerRegisterRequest serverRegisterRequest, String serverId){
        ServerResponse serverResponse = serverService.register(serverId, serverRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serverResponse);
    }

    @GetMapping("/{server-no}")
    public ResponseEntity<ServerResponse> getServerThreshold(@PathVariable("server-no") long serverNo){
        ServerResponse serverResponse = serverService.getServer(serverNo);
        return ResponseEntity
                .ok(serverResponse);
    }

    @PutMapping("/{server-no}")
    public ResponseEntity<ServerResponse> updateServerThreshold(@Validated @RequestBody ServerUpdateRequest serverUpdateRequest, long serverNo){
        ServerResponse serverResponse = serverService.update(serverNo, serverUpdateRequest);
        return ResponseEntity
                .ok(serverResponse);
    }

    @DeleteMapping("/{server-no}")
    public ResponseEntity<Void> deleteServerThreshold(@PathVariable("server-no") long serverNo){
        serverService.delete(serverNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
