package org.restaurante.msvc_autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class MsvcAutenticacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAutenticacionApplication.class, args);
	}

}
