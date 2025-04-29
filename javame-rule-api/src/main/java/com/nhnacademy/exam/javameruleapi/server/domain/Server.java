package com.nhnacademy.exam.javameruleapi.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "servers")
@ToString
@Getter
public class Server {

    //CpuThreshold, MemoryThreshold .. 클래스 분리.. -> Category?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_no", nullable = false)
    private long serverNo;

    @Column(name = "company_domain", nullable = false)
    private String companyDomain; // db에서 찾아서

    @Column(name = "ip_host", unique = true)
    private String iphost;

    public void update(String iphost){
        this.iphost = iphost;
    }

    public Server() { }

    public Server(Long serverNo, String iphost, String companyDomain){
        this.serverNo = serverNo;
        this.iphost = iphost;
        this.companyDomain = companyDomain;
    }

    public static Server ofNewServer(String iphost, String companyDomain){
        return new Server(0L, iphost, companyDomain);
    }


}


