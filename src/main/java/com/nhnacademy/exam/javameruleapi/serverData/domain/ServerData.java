package com.nhnacademy.exam.javameruleapi.serverData.domain;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

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
    private long serverDataNo;

    /**
     *  서버. 양방향 관계에서 일 쪽.
     */
    @ManyToOne
    @JoinColumn(name = "server_no") //외래키
    private Server server;

    /**
     * 서버 데이터의 location입니다.
     * ex) power_meter, server_resource_data 등
     */
    @Column(name = "server_data_location", nullable = false, length = 30)
    private String serverDataLocation;

    /**
     * 서버 데이터의 gateway입니다.
     * ex) modbus
     */
    @Column(name = "server_data_gateway", nullable = false, length = 30)
    private String serverDataGateway;

    /**
     * 서버 데이터의 이름입니다.
     * ex) current_amps, power_watts
     */
    @Column(name = "server_data_name")
    private String serverDataName;

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
     * 서버 데이터 생성 시간.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 영속화 전에 생성 시간을 추가합니다.
     */
    @PrePersist
    protected void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 서버데이터에 서버를 추가하는 메서드
     *
     * @param server 서버
     */
    public void setServer(Server server){
        this.server = server;
    }

    /**
     * 새로운 서버 데이터를 생성하는 생성자.
     *
     * @param server                서버
     * @param serverDataLocation    서버 데이터의 location
     * @param serverDataGateway     서버 데이터의 gateway
     * @param serverDataName        서버 데이터 이름
     * @param minThreshold          서버 데이터의 최소 임계값
     * @param maxThreshold          서버 데이터의 최대 임계값
     */
    public ServerData(Server server, String serverDataLocation, String serverDataGateway,
                      String serverDataName, Double minThreshold, Double maxThreshold) {
        this.server = server;
        this.serverDataLocation = serverDataLocation;
        this.serverDataGateway = serverDataGateway;
        this.serverDataName = serverDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    /**
     * 서버 데이터를 업데이트하는 메서드입니다.
     *
     * @param serverDataLocation 서버 데이터의 location
     * @param serverDataGateway    서버 데이터의 gateway
     * @param serverDataName     서버 데이터 이름
     * @param minThreshold       서버 데이터의 최소 임계값
     * @param maxThreshold       서버 데이터의 최대 임계값
     */
    public void update(String serverDataLocation, String serverDataGateway, String serverDataName,
                       Double minThreshold, Double maxThreshold) {
        this.serverDataLocation = serverDataLocation;
        this.serverDataGateway = serverDataGateway;
        this.serverDataName =serverDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }
}
