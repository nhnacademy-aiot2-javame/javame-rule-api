package com.nhnacademy.exam.javameruleapi.serverData.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 서버 데이터 정보를 나타내는 엔티티 클래스입니다.
 * 이 클래스는 서버와 관련된 데이터를 관리.
 */
@Entity
@Table(name = "server_datas")
@ToString
@Getter
@NoArgsConstructor
public class ServerData {

    /**
     * 서버 데이터의 고유 번호.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_data_no", nullable = false)
    public long serverDataNo;

    /**
     * 서버의 IP 주소입니다.
     */
    @Column(name = "ip_host", nullable = false)
    private String iphost;

    /**
     * 서버 데이터의 카테고리입니다.
     * ex) Performance, Capacity, Network
     */
    @Column(name = "server_data_category", nullable = false, length = 30)
    private String serverDataCategory;

    /**
     * 서버 데이터의 주제입니다.
     * ex) Cpu Usage, Disk Space, Temperature
     */
    @Column(name = "server_data_topic", nullable = false, length = 30)
    private String serverDataTopic;

    /**
     * 서버 데이터의 최소 임계값.
     * 이 값 이하로 떨어지면 경고 발생
     */
    @Column(name = "min_threshold")
    private Double minThreshold; //최소 임계값

    /**
     * 서버 데이터의 최대 임계값.
     * 이 값 이상으로 초과되면 경고 발생.
     */
    @Column(name = "max_threshold")
    private Double maxThreshold; // 최대 임계값

    /**
     * 서버가 속한 회사의 도메인.
     */
    @Column(name = "company_domain")
    private String companyDomain;

    /**
     * 새로운 서버 데이터를 생성하는 생성자.
     *
     * @param iphost                서버의 IP 주소
     * @param serverDataCategory    서버 데이터의 카테고리
     * @param serverDataTopic       서버 데이터의 주제
     * @param minThreshold          서버 데이터의 최소 임계값
     * @param maxThreshold          서버 데이터의 최대 임계값
     * @param companyDomain         서버가 속한 회사의 도메인
     */
    public ServerData(String iphost, String serverDataCategory, String serverDataTopic, Double minThreshold, Double maxThreshold, String companyDomain) {
        this.iphost = iphost;
        this.serverDataCategory = serverDataCategory;
        this.serverDataTopic = serverDataTopic;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.companyDomain = companyDomain;
    }

    /**
     * 서버 데이터를 업데이트하는 메서드입니다.
     *
     * @param serverDataCategory 서버 데이터의 카테고리
     * @param serverDataTopic    서버 데이터의 주제
     * @param minThreshold       서버 데이터의 최소 임계값
     * @param maxThreshold       서버 데이터의 최대 임계값
     */
    public void update(String serverDataCategory, String serverDataTopic, Double minThreshold, Double maxThreshold) {
        this.serverDataCategory = serverDataCategory;
        this.serverDataTopic = serverDataTopic;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }
}
