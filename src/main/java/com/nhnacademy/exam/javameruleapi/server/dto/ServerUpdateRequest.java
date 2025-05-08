package com.nhnacademy.exam.javameruleapi.server.dto;

import lombok.Getter;


/**
 * 서버 IP를 업데이트 할 때 사용하는 요청 DTO 클래스입니다.
 */
@Getter
public class ServerUpdateRequest {

    /**
     * 새로 변경할 IP.
     */
    private String iphost;

    /**
     * 서버 업데이트 요청 생성자.
     *
     * @param iphost 변경할 IP
     */
    public ServerUpdateRequest(String iphost) {

        this.iphost = iphost;
    }

}
