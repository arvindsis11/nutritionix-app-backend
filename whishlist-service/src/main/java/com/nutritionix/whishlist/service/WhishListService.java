package com.nutritionix.whishlist.service;

import org.springframework.http.ResponseEntity;

import com.nutritionix.whishlist.model.WhishList;

public interface WhishListService {

	public ResponseEntity<?> getWhishlistByUserId(String userId);

	public ResponseEntity<?> addFoodItemToWhishlist(WhishList whishlist);

	public ResponseEntity<?> deleteFromWhishList(Long id);
}
