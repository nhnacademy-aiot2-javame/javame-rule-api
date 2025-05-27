package com.nhnacademy.exam.javameruleapi.sensorData.service.Impl;

import com.nhnacademy.exam.javameruleapi.sensor.common.Exception.SensorNotExistException;
import com.nhnacademy.exam.javameruleapi.sensor.domain.Sensor;
import com.nhnacademy.exam.javameruleapi.sensor.repository.SensorRepository;
import com.nhnacademy.exam.javameruleapi.sensorData.common.Exception.AlreadySensorDataExistException;
import com.nhnacademy.exam.javameruleapi.sensorData.common.Exception.SensorDataNotExistException;
import com.nhnacademy.exam.javameruleapi.sensorData.domain.SensorData;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataRegisterRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataResponse;
import com.nhnacademy.exam.javameruleapi.sensorData.dto.SensorDataUpdateRequest;
import com.nhnacademy.exam.javameruleapi.sensorData.repository.SensorDataRepository;
import com.nhnacademy.exam.javameruleapi.sensorData.service.SensorDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * SensorDataService 구현체.
 * 센서 데이터의 등록, 조회, 수정, 삭제 기능을 제공
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SensorDataServiceImpl implements SensorDataService {

    /**
     * 센서 데이터 관련 데이터베이스 작업을 수행하는 리포지토리.
     * 센서 데이터의 CRUD 작업을 처리합니다.
     */
    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;

    /**
     * SensorData 엔티티를 SensorDataResponse로 변환합니다.
     *
     * @param sensorData 변환할 센서 데이터 엔티티
     * @return 변환된 응답 DTO
     */
    SensorDataResponse responseMapper(SensorData sensorData) {
        return new SensorDataResponse(
                sensorData.getSensor().getSensorNo(),
                sensorData.getSensor().getCompanyDomain(),
                sensorData.getSensorDataNo(),
                sensorData.getSensorDataLocation(),
                sensorData.getSensorDataGateway(),
                sensorData.getSensorDataName(),
                sensorData.getMinThreshold(),
                sensorData.getMaxThreshold(),
                sensorData.getCreatedAt()
        );
    }

    /**
     * 센서 데이터를 등록합니다.
     *
     * @param sensorNo                  센서번호
     * @param sensorDataRegisterRequest 등록할 센서 데이터 요청
     * @return 등록된 센서 데이터 응답.
     * @throws AlreadySensorDataExistException 이미 존재하는 센서 데이터일 경우 예외 발생
     */
    @Override
    public SensorDataResponse register(long sensorNo, SensorDataRegisterRequest sensorDataRegisterRequest) {
        boolean isExist = sensorDataRepository.existsBySensor_SensorNoAndSensorDataName(sensorNo, sensorDataRegisterRequest.getSensorDataName());
        if (isExist) {
            throw new AlreadySensorDataExistException("이미 존재하는 센서 데이터 입니다.");
        }
        Sensor foundSensor = sensorRepository.getBySensorNo(sensorNo).
                orElseThrow(() -> new SensorNotExistException("해당 번호의 센서는 존재하지 않습니다."));
        SensorData sensorData = new SensorData(
                foundSensor,
                sensorDataRegisterRequest.getSensorDataGateway(),
                sensorDataRegisterRequest.getSensorDataLocation(),
                sensorDataRegisterRequest.getSensorDataName(),
                sensorDataRegisterRequest.getMinThreshold(),
                sensorDataRegisterRequest.getMaxThreshold()
        );

        sensorDataRepository.save(sensorData);

        return responseMapper(sensorData);
    }


    /**
     * 센서 데이터 번호로 센서 데이터를 조회합니다.
     *
     * @param sensorDataNo 센서 데이터 번호
     * @return 조회된 센서 데이터 응답
     * @throws SensorDataNotExistException 센서 데이터가 존재하지 않는 경우 예외 발생
     */
    @Override
    public SensorDataResponse getSensorDataBySensorDataNo(long sensorDataNo) {
        SensorData sensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDataNo)
                .orElseThrow(() -> new SensorDataNotExistException("존재하지 않는 데이터 타입!"));

        return responseMapper(sensorData);
    }

    /**
     * 센서 번호로 센서 데이터 리스트를 조회합니다.
     *
     * @param sensorNo 센서 번호
     * @return 조회된 센서 데이터 응답.
     * @throws SensorDataNotExistException 센서 데이터가 존재하지 않는 경우 예외 발생
     */
    @Override
    public List<SensorDataResponse> getSensorDatasBySensorNo(Long sensorNo) {
        boolean exists = sensorDataRepository.existsBySensor_SensorNo(sensorNo);
        if (!exists) {
            throw new SensorDataNotExistException("해당 센서번호에 대한 센서 데이터가 존재하지 않습니다.");
        }
        List<SensorData> sensorDataList = sensorDataRepository.getSensorDataBySensor_SensorNo(sensorNo);
        List<SensorDataResponse> sensorDataResponses = new ArrayList<>();
        for (SensorData sensorData : sensorDataList) {
            SensorDataResponse resp = responseMapper(sensorData);
            sensorDataResponses.add(resp);
        }
        return sensorDataResponses;
    }

    /**
     * 센서 데이터를 수정합니다.
     *
     * @param sensorDataNo            수정할 센서 데이터 번호
     * @param sensorDataUpdateRequest 수정 요청 DTO
     * @return 수정된 센서 데이터 응답
     * @throws SensorDataNotExistException 센서 데이터가 존재하지 않는 경우 예외 발생
     */
    @Override
    public SensorDataResponse update(long sensorDataNo, SensorDataUpdateRequest sensorDataUpdateRequest) {
        SensorData foundSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDataNo)
                .orElseThrow(() -> new SensorDataNotExistException("존재하지 않는 데이터 타입! "));
        foundSensorData.update(
                sensorDataUpdateRequest.getSensorDataLocation(),
                sensorDataUpdateRequest.getSensorDataGateway(),
                sensorDataUpdateRequest.getSensorDataName(),
                sensorDataUpdateRequest.getMinThreshold(),
                sensorDataUpdateRequest.getMaxThreshold());
        SensorData updatedSensorData = sensorDataRepository.save(foundSensorData); // 수정된 엔티티 저장.
        return responseMapper(updatedSensorData);

    }

    /**
     * 센서 데이터를 삭제합니다.
     *
     * @param sensorDataNo 삭제할 데이터 번호
     * @return null
     * @throws SensorDataNotExistException SensorData가 존재하지 않는 경우 예외 발생.
     */
    @Override
    public Void delete(long sensorDataNo) {
        SensorData deleteTargetSensorData = sensorDataRepository.getSensorDataBySensorDataNo(sensorDataNo)
                .orElseThrow(() -> new SensorDataNotExistException("존재하지 않는 데이터 타입"));
        sensorDataRepository.delete(deleteTargetSensorData);
        return null;
    }
}
