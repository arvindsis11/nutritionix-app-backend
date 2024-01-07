package com.nutritionix.whishlist.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "whishlist")
public class WhishList {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(hidden = true)
	private Long id;
	private String food_name;
	private String serving_unit;
	private String tag_name;
	private int serving_qty;
	private String tag_id;
	private String photo;
	private String locale;
	@Schema(hidden = true)
	private String userId;
}
