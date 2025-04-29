package com.nhnacademy.exam.javameruleapi.serverData.domain;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "serverdatas")
@ToString
@Getter
public class ServerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_data_no", nullable = false)
    public Long serverDataNo;

    // forein key ... 다대다 인지 일대 다 인지 설정 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_no", nullable = false)
    private Server server;

    @Column(name = "server_data_category", nullable = false, length = 30)
    private String serverDataCategory;

    @Column(name = "server_data_topic", nullable = false, length = 30)
    private String serverDataTopic;

    @Column(name = "min_threshold")
    private Double minThreshold; //최소 임계값

    @Column(name = "max_threshold")
    private Double maxThreshold; // 최대 임계값
}
