package com.salmac.host.service;

import com.salmac.host.entity.ServerEntity;
import com.salmac.host.entity.Status;
import com.salmac.host.repo.ServerRepo;
import com.salmac.host.routine.Constants;
import com.salmac.host.routine.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServerService {

    @Autowired
    ServerRepo serverRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void loadServerLastHeartbeatMap() {
        List<ServerEntity> serverEntityList = serverRepo.getServerEntitiesByStatus(Status.Active);
        if(!serverEntityList.isEmpty()){
            serverEntityList.stream().forEach(e -> Utils.serverLastHeartbeatMap.put(e.getIp(), e.getLatestUptime()));
        }
        System.out.println(Utils.serverLastHeartbeatMap.size());
    }

    public void heartBeat(String ip, String name, String os) {
        Optional<ServerEntity> optionalServerEntity = serverRepo.getServerEntityByIp(ip);
        if (optionalServerEntity.isPresent()) {
            ServerEntity serverEntity = optionalServerEntity.get();
            serverEntity.setLatestDowntime(LocalDateTime.now());
            serverEntity.setStatus(Status.Active);
            serverRepo.save(serverEntity);
        } else {
            ServerEntity serverEntity = ServerEntity.builder().ip(ip).name(name).os(os)
                    .latestUptime(LocalDateTime.now())
                    .status(Status.Active)
                    .build();
            serverRepo.save(serverEntity);
        }
        Utils.serverLastHeartbeatMap.put(ip, LocalDateTime.now());
    }

    public void updateServerStatusScheduler() {
//        Utils.serverLastHeartbeatMap.entrySet().stream()
//                .filter(e-> Duration.between(e.getValue(), LocalDateTime.now()).toMillis() > Constants.SERVER_INACTIVATE_DELAY)
//                .forEach(e -> serverRepo.updateServerStatusByIP(e.getKey()));
        Iterator<Map.Entry<String, LocalDateTime>> iter = Utils.serverLastHeartbeatMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, LocalDateTime> e = iter.next();
            if (Duration.between(e.getValue(), LocalDateTime.now()).toMillis() > Constants.SERVER_INACTIVATE_DELAY) {
                Optional<ServerEntity> optionalServerEntity = serverRepo.getServerEntityByIp(e.getKey());
                if(optionalServerEntity.isPresent()){
                    ServerEntity serverEntity = optionalServerEntity.get();
                    serverEntity.setStatus(Status.Inactive);
                    serverEntity.setLatestDowntime(LocalDateTime.now());
                    serverRepo.save(serverEntity);
                }
                iter.remove();
            }
        }

    }

    public List<ServerEntity> getListOfServer() {
        return serverRepo.findAll();
    }
}
