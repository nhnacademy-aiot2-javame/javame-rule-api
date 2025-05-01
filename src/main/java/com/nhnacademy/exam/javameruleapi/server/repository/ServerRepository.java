package com.nhnacademy.exam.javameruleapi.server.repository;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {

    /**
     *
     * @param iphost
     * @return
     * select * from servers where ip_host=""
     */
    Optional<Server> getServerByIphost(String iphost);

    /**
     *
     * @param companyDomain
     * @return
     */
    Optional<List<Server>> getServersByCompanyDomain(String companyDomain);


    /**
     *
     * @param iphost
     */
    void deleteServerByIphost(String iphost);

    /**
     *
     * @param iphost
     * @return
     */
    Boolean existsServerByIphost(String iphost);

    /**
     *
     * @param serverNo
     * @return
     */
    Optional<Server> getServerByServerNo(long serverNo);

    /**
     *
     * @param serverNo
     * @return
     * select
     */
    Object existsServerByServerNo(long serverNo);
}
