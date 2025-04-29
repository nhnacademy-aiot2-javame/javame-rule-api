package com.nhnacademy.exam.javameruleapi.sensor.service.Impl;

import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.AlreadySensorExistException;
import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.SensorNotExistException;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            throw new AlreadySensorExistException("이미 존재하는 센서입니다.");
        }
        Sensor sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
        return responseMapper(sensor);
    }

    @Override
    public SensorResponse getSensor(long sensorNo) {
        Sensor sensor = sensorRepository.getSensorBySensorNo(sensorNo).orElseThrow(()-> new SensorNotExistException("존재하지 않는 센서 입니다."));

        return responseMapper(sensor);
    }

    @Override
    public List<SensorResponse> getSensors(String companyDomain) {
        List<Sensor> sensors = sensorRepository.getSensorsByCompanyDomain(companyDomain).orElseThrow(()-> new SensorNotExistException("해당 companyDomain으로 센서가 존재하지 않습니다."));
        List<SensorResponse> sensorResponses = new ArrayList<>();
        for (Sensor sensor : sensors){
            sensorResponses.add(new SensorResponse(sensor.getSensorNo(), sensor.getCompanyDomain(), sensor.getSensorId()));
        }
        return sensorResponses;
    }

    @Override
    public void delete(long sensorNo) {
        Sensor sensor = sensorRepository.getSensorBySensorNo(sensorNo).orElseThrow(()-> new SensorNotExistException("존재하지 않는 센서입니다."));
        sensorRepository.delete(sensor);
    }
}
