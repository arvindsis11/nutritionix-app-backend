package com.nutritionix.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonFoodItem {
	private String food_name;
	private String serving_unit;
	private String tag_name;
	private int serving_qty;
	private String tag_id;
	private Photo photo;
	private String locale;
}
