package com.salmac.host.engine;

import com.salmac.host.service.ServerService;
import com.salmac.host.routine.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowCredentials(true).allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
