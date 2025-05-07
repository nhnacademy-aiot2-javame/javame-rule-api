package com.nhnacademy.exam.javameruleapi.serverData.repository;

import com.nhnacademy.exam.javameruleapi.serverData.domain.ServerData;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerDataRepository extends JpaRepository<ServerData, Long> {

    /**
     *
     * @param iphost
     * @return
     */
    Boolean existsServerDataByIphost(String iphost);

    /**
     *
     * @param serverDataNo
     * @return
     */
    Boolean existsServerDataByServerDataNo(long serverDataNo);

    /**
     *
     * @param serverDataNo
     * @return
     */
    ServerData getServerDataByServerDataNo(long serverDataNo);

    /**
     *
     * @param iphost
     * @return
     */
    ServerData getServerDataByIphost(String iphost);
}
