package com.nutritionix.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NutritionixServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutritionixServiceApplication.class, args);
	}

}
