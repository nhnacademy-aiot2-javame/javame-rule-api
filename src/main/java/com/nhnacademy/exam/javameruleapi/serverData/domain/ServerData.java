package com.nhnacademy.exam.javameruleapi.serverData.domain;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "server_datas")
@ToString
@Getter
@NoArgsConstructor
public class ServerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_data_no", nullable = false)
    public long serverDataNo;

    @Column(name = "ip_host", nullable = false)
    private String iphost;

    @Column(name = "server_data_category", nullable = false, length = 30)
    private String serverDataCategory;

    @Column(name = "server_data_topic", nullable = false, length = 30)
    private String serverDataTopic;

    @Column(name = "min_threshold")
    private Double minThreshold; //최소 임계값

    @Column(name = "max_threshold")
    private Double maxThreshold; // 최대 임계값

    public ServerData(String iphost, String serverDataCategory, String serverDataTopic, Double minThreshold, Double maxThreshold) {
        this.iphost = iphost;
        this.serverDataCategory = serverDataCategory;
        this.serverDataTopic = serverDataTopic;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public void update(String serverDataCategory, String serverDataTopic, Double minThreshold, Double maxThreshold){
        this.serverDataCategory = serverDataCategory;
        this.serverDataTopic = serverDataTopic;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }
}
