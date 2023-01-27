package com.example.serverv6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class ServerV6Application {

	public static void main(String[] args) {
		SpringApplication.run(ServerV6Application.class, args);
	}

}
