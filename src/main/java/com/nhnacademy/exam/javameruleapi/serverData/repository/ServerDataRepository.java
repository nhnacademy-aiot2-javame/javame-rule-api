package com.nhnacademy.exam.javameruleapi.serverData.repository;

import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 서버 데이터에 대한 CRUD 및 조회 기능을 제공하는 JPA 리포지토리입니다.
 */
public interface ServerDataRepository extends JpaRepository<ServerData, Long> {

    /**
     * 지정된 IP 호스트를 가진 서버 데이터가 존재하는지 확인합니다.
     *
     * @param iphost 확인할 IP 호스트 주소
     * @return 해당 IP 호스트가 존재하면 true, 아니면 false
     */
    Boolean existsServerDataByIphost(String iphost);

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
     *  IP 호스트를 기준으로 서버 데이터를 조회합니다.
     *
     * @param iphost 조회할 IP 호스트 주소
     * @return 조회된 ServerData 객체
     */
    ServerData getServerDataByIphost(String iphost);
}
