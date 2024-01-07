package com.nutritionix.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.app.service.NutritionixService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/v1.0/nutrition")
@CrossOrigin("*")
public class NutritionixController {

	private final NutritionixService nutritionixService;

	public NutritionixController(NutritionixService nutritionixService) {
		this.nutritionixService = nutritionixService;
	}
	
	@GetMapping("/foodItem/{query}")
	@Operation(summary = "get common food items by name")
	public ResponseEntity<?> getCommonFoodItems(@PathVariable String query,@Parameter(hidden = true) @RequestHeader("Authorization") String token){
		
		return nutritionixService.getCommonFoodItems(query);	
	}
	
	@GetMapping("/getNutrients")
	@Operation(summary = "get common food nutrients by name")
	public ResponseEntity<?> getFoodNutritions(@RequestParam String foodName,@Parameter(hidden = true) @RequestHeader("Authorization") String token){
		
		return nutritionixService.getFoodNutritions(foodName);	
	}
	
	
}
