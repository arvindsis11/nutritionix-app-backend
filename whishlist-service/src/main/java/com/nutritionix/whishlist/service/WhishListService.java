package com.nutritionix.whishlist.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nutritionix.whishlist.model.WhishList;

public interface WhishListService {

	public List<WhishList> getWhishlistByUserId(String userId);

	public ResponseEntity<?> addFoodItemToWhishlist(WhishList whishlist);

	public ResponseEntity<?> deleteFromWhishList(Long id);
}
