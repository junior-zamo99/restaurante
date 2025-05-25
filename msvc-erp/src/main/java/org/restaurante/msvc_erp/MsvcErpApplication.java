package org.restaurante.msvc_erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcErpApplication.class, args);
	}

}
