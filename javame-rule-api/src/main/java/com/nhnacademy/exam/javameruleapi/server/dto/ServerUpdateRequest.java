package com.nhnacademy.exam.javameruleapi.server.dto;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ServerUpdateRequest {

    public String iphost;

    public ServerUpdateRequest(String iphost){
        this.iphost = iphost;
    }

}
