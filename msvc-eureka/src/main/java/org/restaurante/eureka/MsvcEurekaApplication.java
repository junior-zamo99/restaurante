package org.restaurante.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication

public class MsvcEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcEurekaApplication.class, args);
	}

}
