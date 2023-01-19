package com.insulinhero.serverv5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class ServerV5Application {

	public static void main(String[] args) {
		SpringApplication.run(ServerV5Application.class, args);
	}

}
