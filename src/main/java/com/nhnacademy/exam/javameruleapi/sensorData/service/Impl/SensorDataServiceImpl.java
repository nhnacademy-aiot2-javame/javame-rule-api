//package com.nhnacademy.exam.javameruleapi.sensorData.service.Impl;
//
//import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
//import com.nhnacademy.exam.javameruleapi.sensorData.common.Exception.AlreadyDataTypeExistException;
//import com.nhnacademy.exam.javameruleapi.sensorData.common.Exception.DataTypeNotExistException;
//import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
//import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;
//import com.nhnacademy.exam.javameruleapi.sensorData.repository.SensorDataRepository;
//import com.nhnacademy.exam.javameruleapi.sensorData.service.SensorDataService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class SensorDataServiceImpl implements SensorDataService {
//
//    private final SensorDataRepository sensorDataRepository;
//
//
//    SensorDataResponse responseMapper(SensorData sensorData){
//        return new SensorDataResponse(
//                sensorData.getSensorDataNo(),
//                sensorData.getSensorDataName(),
//                sensorData.getMinThreshold(),
//                sensorData.getMaxThreshold()
//        );
//    }
//
//    @Override
//    public SensorDataResponse register(long sensorNo, SensorDataRegisterRequest sensorDataRegisterRequest) {
//        Boolean isExist = sensorDataRepository.existsDataTypeByDataTypeName(sensorDataRegisterRequest.getSensorDataName());
//        if(isExist){
//            throw new AlreadyDataTypeExistException("이미 존재하는 데이터 타입입니다.");
//        }
//        SensorData sensorData = new SensorData(sensorNo, sensorDataRegisterRequest.getSensorDataName(),
//                sensorDataRegisterRequest.getMinThreshold(), sensorDataRegisterRequest.getMaxThreshold());
//
//        sensorDataRepository.save(sensorData);
//
//        return responseMapper(sensorData);
//    }
//
//    @Override
//    public SensorDataResponse getSensorData(long dataTypeNo) {
//        SensorData sensorData = sensorDataRepository.getSensorDataBySensorDataNo(dataTypeNo)
//                .orElseThrow(()-> new DataTypeNotExistException("존재하지 않는 데이터 타입!"));
//
//        return responseMapper(sensorData);
//    }
//
//    @Override
//    public SensorDataResponse update(long sensorDataNo, SensorDataUpdateRequest sensorDataUpdateRequest) {
//      SensorData foundSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDataNo)
//              .orElseThrow(()-> new DataTypeNotExistException("존재하지 않는 데이터 타입! "));
//      foundSensorData.update(sensorDataUpdateRequest.getSensorDataName(), sensorDataUpdateRequest.getMinThreshold(), sensorDataUpdateRequest.getMaxThreshold());
//      SensorData updatedSensorData = sensorDataRepository.save(foundSensorData); // 수정된 엔티티 저장.
//      return responseMapper(updatedSensorData);
//
//    }
//
//    @Override
//    public Void delete(long sensorDataNo) {
//        SensorData deleteTargetSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDataNo)
//                .orElseThrow(()-> new DataTypeNotExistException("존재하지 않는 데이터 타입"));
//        sensorDataRepository.delete(deleteTargetSensorData);
//        return null;
//    }
//}
