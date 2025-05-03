//package com.nhnacademy.exam.javameruleapi.sensor.service.Impl;
//
//import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.AlreadySensorExistException;
//import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.SensorNotExistException;
//import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.sensor.dto.SensorResponse;
//import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
//import com.nhnacademy.exam.javameruleapi.sensor.service.SensorService;
//import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
//import com.nhnacademy.exam.javameruleapi.sensorData.repository.SensorDataRepository;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Transactional
//@Service
//public class SensorServiceImpl implements SensorService {
//
//    private final SensorRepository sensorRepository;
//    private final SensorDataRepository sensorDataRepository;
//
//    public SensorServiceImpl(SensorRepository sensorRepository, SensorDataRepository sensorDataRepository) {
//        this.sensorRepository = sensorRepository;
//        this.sensorDataRepository = sensorDataRepository;
//    }
//
//    SensorResponse responseMapper(Sensor sensor){
//
//        List<SensorDataResponse> sensorDataResponses = sensor.getSensorDataList().stream()
//                .map(data -> new SensorDataResponse(
//                        data.getSensorDataNo(),
//                        data.getSensorDataName(),
//                        data.getMinThreshold(),
//                        data.getMaxThreshold()
//                ))
//                .toList();
//
//        return new SensorResponse(sensor.getSensorNo(), sensor.getCompanyDomain(), sensor.getSensorId(), sensorDataResponses);
//    }
//
//    @Override
//    public SensorResponse register(SensorRegisterRequest sensorRegisterRequest) {
//
//        // 센서아이디와 companyDomain이 둘다 존재할 때 에러 발생.
//        boolean isDuplicated = sensorRepository.existsSensorBySensorIdAndCompanyDomain(
//                sensorRegisterRequest.getSensorId(),
//                sensorRegisterRequest.getCompanyDomain()
//        );
//
//        if(isDuplicated){
//            throw new AlreadySensorExistException("이미 존재하는 센서입니다.");
//        }
//
//        Sensor sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
//        Sensor savedSensor = sensorRepository.save(sensor);
//
//        // 센서 이름만 받으니까 이름만으로 SensorData 객체 만들기
//        List<SensorData> sensorDataList = sensorRegisterRequest.getSensorDataList().stream()
//                .map(dto -> new SensorData(sensor, dto.getSensorDataName(), null, null )) // minThreshold, maxThreshold는 null로 설정
//                .toList();
//
//        sensorDataRepository.saveAll(sensorDataList);
//
//        return responseMapper(savedSensor);
//    }
//
//    @Override
//    public SensorResponse getSensor(long sensorNo) {
//        Sensor sensor = sensorRepository.getSensorBySensorNo(sensorNo).orElseThrow(()-> new SensorNotExistException("존재하지 않는 센서 입니다."));
//
////        List<SensorDataResponse> sensorDataResponseList = sensor.getSensorDataList().stream()
////                .map(data -> new SensorDataResponse(
////                        data.getSensorDataNo(),
////                        data.getSensor(),
////                        data.getSensorDataName(),
////                        data.getMinThreshold(),
////                        data.getMaxThreshold()
////                )).toList();
//
//        return responseMapper(sensor);
//    }
//
//    @Override
//    public List<SensorResponse> getSensors(String companyDomain) {
//        List<Sensor> sensors = sensorRepository.getSensorsByCompanyDomain(companyDomain).orElseThrow(()-> new SensorNotExistException("해당 companyDomain으로 센서가 존재하지 않습니다."));
//        List<SensorResponse> sensorResponses = new ArrayList<>();
//        for (Sensor sensor : sensors){
//            sensorResponses.add(responseMapper(sensor));
//        }
//        return sensorResponses;
//    }
//
//    @Override
//    public Void delete(long sensorNo) {
//        Sensor sensor = sensorRepository.getSensorBySensorNo(sensorNo).orElseThrow(()-> new SensorNotExistException("존재하지 않는 센서입니다."));
//        sensorRepository.delete(sensor);
//        return null;
//    }
//}
