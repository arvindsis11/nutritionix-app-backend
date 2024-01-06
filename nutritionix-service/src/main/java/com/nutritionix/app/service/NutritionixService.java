package com.nutritionix.app.service;

import org.springframework.http.ResponseEntity;

public interface NutritionixService {

	public ResponseEntity<?> getCommonFoodItems(String foodName);
	
	public ResponseEntity<?> getFoodNutritions(String foodName);
}
