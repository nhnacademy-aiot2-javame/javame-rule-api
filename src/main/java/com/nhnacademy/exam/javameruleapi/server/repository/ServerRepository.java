package com.nhnacademy.exam.javameruleapi.server.repository;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Server 엔티티에 대해 CRUD 및 커스텀 query를 처리하는 Spring Data JPA Repository입니다.
 */
public interface ServerRepository extends JpaRepository<Server, Long> {


    /**
     * companyDomain으로 서버 리스트를 조회합니다.
     *
     * @param companyDomain 회사 도메인
     * @return select * from servers where company_domain = :companyDomain
     */
    Optional<List<Server>> getServersByCompanyDomain(String companyDomain);


    /**
     * iphost로 서버를 삭제합니다.
     *
     * @param iphost 삭제할 서버의 IP
     * delete from servers where ip_host = :iphost
     */
    void deleteServerByIphost(String iphost);



    /**
     * serverNo로 서버를 조회합니다.
     *
     * @param serverNo 서버 고유 번호
     * @return select * from servers where server_no = : serverNo
     */
    Optional<Server> getServerByServerNo(long serverNo);

    /**
     * companyDomain과 iphost로 서버가 존재하는지 확인합니다.
     * <p>
     * server 등록 시 사용
     *
     * @param companyDomain
     * @param iphost
     * @return
     */
    Boolean existsByCompanyDomainAndIphost(String companyDomain, String iphost);

    /**
     * 서버 번호로 서버의 존재 여부 확인.
     *
     * @param serverNo
     * @return
     */
    Boolean existsServerByServerNo(long serverNo);

    /**
     *
     * @param serverNo
     */
    void deleteServerByServerNo(long serverNo);
}
