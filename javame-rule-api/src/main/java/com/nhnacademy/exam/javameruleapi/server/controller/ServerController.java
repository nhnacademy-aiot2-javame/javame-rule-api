package com.nhnacademy.exam.javameruleapi.server.controller;

import com.nhnacademy.exam.javameruleapi.server.dto.ServerResponse;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerThresholdRegisterRequest;
import com.nhnacademy.exam.javameruleapi.server.dto.ServerThresholdUpdateRequest;
import com.nhnacademy.exam.javameruleapi.server.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @PostMapping
    public ResponseEntity<ServerResponse> registerServerThreshold(@Validated @RequestBody ServerThresholdRegisterRequest serverThresholdRegisterRequest){
        ServerResponse serverResponse = serverService.register(serverThresholdRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serverResponse);
    }

    @GetMapping("/{server-no}")
    public ResponseEntity<ServerResponse> getServerThreshold(@PathVariable("server-no") long serverNo){
        ServerResponse serverResponse = serverService.getServerThreshold(serverNo);
        return ResponseEntity
                .ok(serverResponse);
    }

    @PutMapping("/{server-no}")
    public ResponseEntity<ServerResponse> updateServerThreshold(@Validated @RequestBody ServerThresholdUpdateRequest serverThresholdUpdateRequest){
        ServerResponse serverResponse = serverService.update(serverThresholdUpdateRequest);
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
