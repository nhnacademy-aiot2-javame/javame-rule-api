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

/**
 * SensorService의 구현 클래스입니다.
 * <p>
 * 이 클래스는 센서 등록, 조회, 삭제와 관련된 비즈니스 로직을 처리합니다.
 */
@Slf4j
@Transactional
@Service
public class SensorServiceImpl implements SensorService {

    /**
     * 센서 정보를 저장하고 조회하는 리포지토리.
     */
    private final SensorRepository sensorRepository;

    /**
     * SensorRepository를 주입받습니다.
     *
     * @param sensorRepository 센서 정보를 저장하고 조회하는 리포지토리.
     */
    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    /**
     * Sensor 객체를 SensorResponse 객체로 변환합니다.
     *
     * @param sensor 변환할 센서 객체
     * @return 변환된 센서 응답 객체
     */
    SensorResponse responseMapper(Sensor sensor) {
        return new SensorResponse(
                sensor.getSensorNo(),
                sensor.getCompanyDomain(),
                sensor.getSensorId(),
                sensor.getCreated_at()
        );
    }

    /**
     * 새로운 센서를 등록합니다.
     * <p>
     * 센서 ID와 회사 도메인이 이미 존재하는 경우 AlreadySensorExistException을 발생시킵니다.
     *
     * @param sensorRegisterRequest 센서 등록에 필요한 정보가 담긴 요청 객체
     * @return 등록된 센서의 응답 데이터
     * @throws AlreadySensorExistException 이미 존재하는 센서가 있을 경우 발생
     */
    @Override
    public SensorResponse register(SensorRegisterRequest sensorRegisterRequest) {

        // 센서아이디와 companyDomain이 둘다 존재할 때 에러 발생.
        boolean isDuplicated = sensorRepository.existsSensorBySensorIdAndCompanyDomain(
                sensorRegisterRequest.getSensorId(),
                sensorRegisterRequest.getCompanyDomain()
        );

        if (isDuplicated) {
            throw new AlreadySensorExistException("이미 존재하는 센서입니다.");
        }

        Sensor sensor = new Sensor(sensorRegisterRequest.getCompanyDomain(), sensorRegisterRequest.getSensorId());
        Sensor savedSensor = sensorRepository.save(sensor);

        return responseMapper(savedSensor);
    }

    /**
     * 주어진 센서 번호에 해당하는 센서를 조회합니다.
     * <p>
     * 센서가 존재하지 않으면 SensorNotExistException을 발생시킵니다.
     *
     * @param sensorNo 조회할 센서의 고유 번호
     * @return 조회된 센서의 응답 데이터
     * @throws SensorNotExistException 센서가 존재하지 않는 경우 발생
     */
    @Override
    public SensorResponse getSensor(long sensorNo) {
        Sensor sensor = sensorRepository.getSensorBySensorNo(sensorNo).orElseThrow(() -> new SensorNotExistException("존재하지 않는 센서 입니다."));
        return responseMapper(sensor);
    }

    /**
     * 주어진 회사 도메인에 해당하는 모든 센서를 조회합니다.
     * <p>
     * 센서가 존재하지 않으면 SensorNotExistException을 발생시킵니다.
     *
     * @param companyDomain 센서가 속한 회사 도메인
     * @return 조회된 센서들의 응답 데이터 리스트
     * @throws SensorNotExistException 해당 도메인에 센서가 존재하지 않을 경우 발생
     */
    @Override
    public List<SensorResponse> getSensors(String companyDomain) {
        List<Sensor> sensors = sensorRepository.getSensorsByCompanyDomain(companyDomain).orElseThrow(() -> new SensorNotExistException("해당 companyDomain으로 센서가 존재하지 않습니다."));
        List<SensorResponse> sensorResponses = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorResponses.add(responseMapper(sensor));
        }
        return sensorResponses;
    }

    /**
     * 주어진 센서 번호에 해당하는 센서를 삭제합니다.
     * <p>
     * 센서가 존재하지 않으면 SensorNotExistException을 발생시킵니다.
     *
     * @param sensorNo 삭제할 센서의 고유 번호
     * @return 삭제 후 응답 데이터 (void)
     * @throws SensorNotExistException 센서가 존재하지 않는 경우 발생
     */
    @Override
    public Void delete(long sensorNo) {
        Sensor sensor = sensorRepository.getSensorBySensorNo(sensorNo).orElseThrow(() -> new SensorNotExistException("존재하지 않는 센서입니다."));
        sensorRepository.delete(sensor);
        return null;
    }
}
