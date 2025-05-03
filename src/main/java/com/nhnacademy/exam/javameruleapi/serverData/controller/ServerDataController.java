package com.nhnacademy.exam.javameruleapi.serverData.controller;

import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataResponse;
import com.nhnacademy.exam.javameruleapi.serverData.dto.ServerDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.serverData.service.ServerDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server-datas")
public class ServerDataController {

    private final ServerDataService serverDataService;


    public ServerDataController(ServerDataService serverDataService) {
        this.serverDataService = serverDataService;
    }

    @PostMapping
    public ResponseEntity<ServerDataResponse> registerServerData(@Validated @RequestBody ServerDataRegisterRequest serverDataRegisterRequest){
        ServerDataResponse serverDataResponse = serverDataService.registerServerData(serverDataRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serverDataResponse);
    }

    @GetMapping("/{server-data-no}")
    public ResponseEntity<ServerDataResponse> getServerData(@PathVariable ("server-data-no") long serverDataNo){
        ServerDataResponse serverDataResponse = serverDataService.getServerData(serverDataNo);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    @PutMapping("/{server-data-no}")
    public ResponseEntity<ServerDataResponse> updateServerData(@PathVariable long serverDataNo, @Validated ServerDataUpdateRequest serverDataUpdateRequest){
        ServerDataResponse serverDataResponse = serverDataService.updateServerData(serverDataNo, serverDataUpdateRequest);
        return ResponseEntity
                .ok(serverDataResponse);
    }

    @DeleteMapping("/{server-data-no}")
    public ResponseEntity<Void> deleteServerData(@PathVariable ("server-data-no") long serverDataNo){
        Void serverDataResponse = serverDataService.delete(serverDataNo);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
