package com.salmac.host;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.salmac.host.files.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class HostApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostApplication.class, args);
	}

}
