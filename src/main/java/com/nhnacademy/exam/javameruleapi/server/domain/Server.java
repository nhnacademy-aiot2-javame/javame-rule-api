package com.nhnacademy.exam.javameruleapi.server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

/**
 *  서버 정보를 나타내는 엔티티 클래스.
 *  서버 번호, IP 주소, 회사 도메인을 포함.
 */
@Entity
@Table(name = "servers")
@ToString
@Getter
public class Server {


    /**
     * 서버의 고유 식별 번호 (자동생성).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_no", nullable = false)
    private long serverNo;

    /**
     * 서버가 속한 회사의 도메인.
     */
    @Column(name = "company_domain", nullable = false)
    private String companyDomain; // db에서 찾아서

    /**
     * 서버의 IP 또는 호스트명 (고유).
     */
    @Column(name = "iphost", unique = true)
    private String iphost;


    /**
     * 서버의 IP 또는 호스트명을 수정합니다.
     *
     * @param iphost 수정할 IP
     */
    public void update(String iphost) {

        this.iphost = iphost;
    }

    /**
     *  기본 생성자 (JPA 사용을 위함).
     */
    public Server() {
    }

    /**
     * 서버 객체를 생성하는 생성자.
     *
     * @param iphost IP 또는 호스트명
     * @param companyDomain 회사 도메인
     */
    public Server(String iphost, String companyDomain) {
        this.iphost = iphost;
        this.companyDomain = companyDomain;
    }

    /**
     * 새 서버 객체를 생성하는 정적 팩토리 메서드.
     *
     * @param iphost
     * @param companyDomain
     * @return 새로 생성된 Server 객체
     */
    public static Server ofNewServer(String iphost, String companyDomain) {
        return new Server(iphost, companyDomain);
    }


}


