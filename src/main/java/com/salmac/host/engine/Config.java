package com.salmac.host.engine;

import com.salmac.host.service.ServerService;
import com.salmac.host.routine.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableScheduling
public class Config {


    @Autowired
    ServerService serverService;

    @Scheduled(fixedRate = Constants.SERVER_STATUS_CHECK_INTERVAL)
    public void scheduleFixedRateTask() {
        serverService.updateServerStatusScheduler();
        //Utils.serverLastHeartbeatMap.entrySet().forEach(e-> System.out.println(e.getKey()));
    }
}
