package com.nutritionix.whishlist.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nutritionix.whishlist.model.WhishList;

@Service
public class WhishListServiceImpl implements WhishListService{

	@Override
	public ResponseEntity<?> getWhishlistByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> addFoodItemToWhishlist(WhishList whishlist) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteFromWhishList(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
