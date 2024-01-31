package com.nutritionix.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.app.exception.ExternalServiceException;
import com.nutritionix.app.exception.InvalidCredentialsException;
import com.nutritionix.app.feign.AuthClient;
import com.nutritionix.app.service.NutritionixService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/v1.0/nutrition")
@CrossOrigin("*")
public class NutritionixController {

	//added exception handling
	private final NutritionixService nutritionixService;

	private final AuthClient authClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NutritionixController.class);

	public NutritionixController(NutritionixService nutritionixService, AuthClient authClient) {
		this.nutritionixService = nutritionixService;
		this.authClient = authClient;
	}

	@GetMapping("/foodItem/{query}")
	@Operation(summary = "get common food items by name")
	public ResponseEntity<?> getCommonFoodItems(@PathVariable String query,
			@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
		try {
			Map<String, String> userInfo = (Map<String, String>) authClient.validateToken(token).getBody();
			if (userInfo.containsValue("ROLE_ADMIN") || userInfo.containsValue("ROLE_CUSTOMER")) {
				LOGGER.info("Inside: NutritionixController.getCommonFoodItems");
				return nutritionixService.getCommonFoodItems(query);
			} else {
				throw new InvalidCredentialsException("Access Denied");
			}
		} catch (FeignException e) {
			LOGGER.error("Inside: NutritionixController.getCommonFoodItems { }", e.getMessage());
			throw new ExternalServiceException(e.getMessage());
		}
	}

	@GetMapping("/getNutrients")
	@Operation(summary = "get common food nutrients by name")
	public ResponseEntity<?> getFoodNutritions(@RequestParam String foodName,
			@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
		try {
			Map<String, String> userInfo = (Map<String, String>) authClient.validateToken(token).getBody();
			if (userInfo.containsValue("ROLE_ADMIN") || userInfo.containsValue("ROLE_CUSTOMER")) {
				LOGGER.info("Inside: NutritionixController.getFoodNutritions");
				return nutritionixService.getFoodNutritions(foodName);
			} else {
				throw new InvalidCredentialsException("Access Denied");
			}
		} catch (FeignException e) {
			LOGGER.error("Inside: NutritionixController.getFoodNutritions { }", e.getMessage());
			throw new ExternalServiceException(e.getMessage());
		}
	}

}
