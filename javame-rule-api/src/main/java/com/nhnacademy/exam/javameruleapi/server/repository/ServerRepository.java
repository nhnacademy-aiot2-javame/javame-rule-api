package com.nhnacademy.exam.javameruleapi.server.repository;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {

    Optional<Server> getServerByIphost(String iphost);


    List<Server> getServersByCompanyDomain(String companyDomain);

    Optional<Server> getServerByServerId(String serverId);

    void deleteServerByIphost(String iphost);
}
