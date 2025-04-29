package com.nhnacademy.exam.javameruleapi.dataType.service.Impl;

import com.nhnacademy.exam.javameruleapi.dataType.common.Exception.AlreadyDataTypeExistException;
import com.nhnacademy.exam.javameruleapi.dataType.common.Exception.DataTypeNotExistException;
import com.nhnacademy.exam.javameruleapi.dataType.domain.DataType;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeRegisterRequest;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeResponse;
import com.nhnacademy.exam.javameruleapi.dataType.dto.DataTypeUpdateRequest;
import com.nhnacademy.exam.javameruleapi.dataType.repository.DataTypeRepository;
import com.nhnacademy.exam.javameruleapi.dataType.service.DataTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DataTypeServiceImpl implements DataTypeService {

    private final DataTypeRepository dataTypeRepository;


    DataTypeResponse responseMapper(DataType dataType){
        return new DataTypeResponse(
                dataType.getDataTypeNo(),
                dataType.getSensor(),
                dataType.getDataTypeName(),
                dataType.getMinThreshold(),
                dataType.getMaxThreshold()
        );
    }

    @Override
    public DataTypeResponse register(DataTypeRegisterRequest dataTypeRegisterRequest) {
        Boolean isExist = dataTypeRepository.existsDataTypeByDataTypeName(dataTypeRegisterRequest.getDataTypeName());
        if(isExist){
            throw new AlreadyDataTypeExistException("이미 존재하는 데이터 타입입니다.");
        }
        DataType dataType = new DataType(dataTypeRegisterRequest.getSensor(), dataTypeRegisterRequest.getDataTypeName(),
                dataTypeRegisterRequest.getMinThreshold(), dataTypeRegisterRequest.getMaxThreshold());

        dataTypeRepository.save(dataType);

        return responseMapper(dataType);
    }

    @Override
    public DataTypeResponse getDataType(long dataTypeNo) {
        DataType dataType = dataTypeRepository.getDataTypeByDataTypeNo(dataTypeNo)
                .orElseThrow(()-> new DataTypeNotExistException("존재하지 않는 데이터 타입!"));

        return responseMapper(dataType);
    }

    @Override
    public DataTypeResponse update(long dataTypeNo, DataTypeUpdateRequest dataTypeUpdateRequest) {
      DataType foundDataType = dataTypeRepository.getDataTypeByDataTypeNo(dataTypeNo)
              .orElseThrow(()-> new DataTypeNotExistException("존재하지 않는 데이터 타입! "));
      foundDataType.update(dataTypeUpdateRequest.getDataTypeName(), dataTypeUpdateRequest.getMinThreshold(), dataTypeUpdateRequest.getMaxThreshold());
      DataType updatedDataType = dataTypeRepository.save(foundDataType); // 수정된 엔티티 저장.
      return responseMapper(updatedDataType);

    }

    @Override
    public Void delete(long dataTypeNo) {
        DataType deleteTarget = dataTypeRepository.getDataTypeByDataTypeNo(dataTypeNo)
                .orElseThrow(()-> new DataTypeNotExistException("존재하지 않는 데이터 타입"));
        dataTypeRepository.delete(deleteTarget);
        return null;
    }
}
