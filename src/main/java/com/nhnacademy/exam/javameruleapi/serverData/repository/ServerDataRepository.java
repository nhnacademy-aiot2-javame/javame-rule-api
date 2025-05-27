package com.nhnacademy.exam.javameruleapi.serverData.repository;

import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 서버 데이터에 대한 CRUD 및 조회 기능을 제공하는 JPA 리포지토리입니다.
 */
public interface ServerDataRepository extends JpaRepository<ServerData, Long> {

    /**
     * 서버 번호와 서버 데이터 이름을 가지고 서버 데이터가 존재하는지 이중 체크합니다.
     *
     * @param serverNo
     * @return 서버 번호와 서버 데이터 이름으로 조회해서 서버 데이터가 존재하면 true, 아니면 false
     */
    Boolean existsByServer_ServerNoAndServerDataName(long serverNo, String serverDataName);

    /**
     * 지정된 서버 데이터 번호를 가진 데이터가 존재하는지 확인합니다.
     *
     * @param serverDataNo 서버 데이터 번호
     * @return 해당 번호를 가진 데이터가 존재하면 true, 아니면 false
     */
    Boolean existsServerDataByServerDataNo(long serverDataNo);

    /**
     * 서버 데이터 번호를 기준으로 데이터를 조회합니다.
     *
     * @param serverDataNo 서버 데이터 번호
     * @return 조회된 ServerData 객체
     */
    ServerData getServerDataByServerDataNo(long serverDataNo);

    /**
     *
     * @param serverNo 서버 번호
     * @return 조회된 ServerData 리스트
     */
    List<ServerData> getServerDataByServer_ServerNo(long serverNo);


}
