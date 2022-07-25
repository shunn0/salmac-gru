package com.salmac.host.repo;

import com.salmac.host.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepo extends JpaRepository<ServerEntity, Long> {

    public Optional<ServerEntity> getServerEntityByIp(String Ip);

    @Modifying
    @Query("update ServerEntity s set s.status = 'inactive' where s.ip = ?1")
    public void updateServerStatusByIP(String IP);
}