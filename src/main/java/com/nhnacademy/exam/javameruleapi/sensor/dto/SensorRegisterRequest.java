package com.nhnacademy.exam.javameruleapi.sensor.dto;

import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SensorRegisterRequest {


    private String companyDomain;

    private String sensorId;


}
