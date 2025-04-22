package com.nhnacademy.exam.javameruleapi.dataType.domain;

import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "dataTypes")
@ToString
@NoArgsConstructor
@Getter
public class DataType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataType_no", nullable = false)
    private long dataTypeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_no",
            foreignKey = @ForeignKey(name = "fk_sensor", foreignKeyDefinition = "FOREIGN KEY (sensor_no) REFERENCES sensors(sensor_no) ON DELETE CASCADE")
            )
    private Sensor sensor;

    @Column(name = "dataType_name", nullable = false)
    private String dataTypeName;

    @Column(name = "threshold")
    private Double threshold;

    public DataType(Sensor sensor, String dataTypeName, Double threshold){
        this.sensor = sensor;
        this.dataTypeName = dataTypeName;
        this.threshold = threshold;
    }

}
