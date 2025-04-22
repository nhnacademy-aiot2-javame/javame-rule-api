package com.nhnacademy.exam.javameruleapi.sensor.service.Impl;

import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.AlreadyExistException;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Transactional
@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    SensorResponse responseMapper(Sensor sensor){
        return new SensorResponse(sensor.getSensorNo(), sensor.getCompanyDomain(), sensor.getSensorId());
    }

    @Override
    public SensorResponse register(SensorRegisterRequest sensorRegisterRequest) {
        Boolean isExist = sensorRepository.existsSensorBySensorId(sensorRegisterRequest.getSensorId());
        if(isExist){
            throw new AlreadyExistException("이미 존재하는 센서입니다.");
        }
        Sensor sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
        return responseMapper(sensor);
    }

    @Override
    public SensorResponse getSensor(long sensorNo) {
        Sensor sensor = sensorRepository.getSensorBySensorNo(sensorNo);
        return responseMapper(sensor);
    }

    @Override
    public SensorResponse update(SensorUpdateRequest sensorUpdateRequest) {
        Sensor targetSensor = sensorRepository.getSensorBySensorNo(sensorUpdateRequest.getSensorNo());
        sensorRepository.update(targetSensor);
        return responseMapper(targetSensor);
    }

    @Override
    public void delete(long sensorNo) {
        Sensor target = sensorRepository.getSensorBySensorNo(sensorNo);
        sensorRepository.delete(target);
    }
}
