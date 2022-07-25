package com.salmac.host.engine;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.salmac.host.service.ServerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Config.class})
@ExtendWith(SpringExtension.class)
class ConfigTest {
    @Autowired
    private Config config;

    @MockBean
    private ServerService serverService;
}

