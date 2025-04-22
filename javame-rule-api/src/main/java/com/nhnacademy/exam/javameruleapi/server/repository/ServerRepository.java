package com.nhnacademy.exam.javameruleapi.server.repository;

import com.nhnacademy.exam.javameruleapi.server.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {

    Boolean existsServerByServerId(String serverId);

    Server getServerByServerNo(long serverNo);
}
