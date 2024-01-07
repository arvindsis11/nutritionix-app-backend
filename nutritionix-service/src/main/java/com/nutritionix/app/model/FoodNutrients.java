package com.nutritionix.app.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodNutrients {
	private String food_name;
	private String brand_name;
	private double serving_qty;
	private String serving_unit;
	private double serving_weight_grams;
	private double nf_calories;
	private double nf_total_fat;
	private double nf_saturated_fat;
	private double nf_cholesterol;
	private double nf_sodium;
	private double nf_total_carbohydrate;
	private double nf_dietary_fiber;
	private double nf_sugars;
	private double nf_protein;
	private double nf_potassium;
	private double nf_p;
	private List<FullNutrient> full_nutrients;
	// alt measures --fix
	private Photo photo;

}
