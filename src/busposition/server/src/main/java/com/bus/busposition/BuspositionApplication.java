package com.bus.busposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BuspositionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuspositionApplication.class, args);
	}
}
