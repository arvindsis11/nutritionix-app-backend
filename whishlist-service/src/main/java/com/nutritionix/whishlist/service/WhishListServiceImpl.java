package com.nutritionix.whishlist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nutritionix.whishlist.exception.ResourceAlreadyExistsException;
import com.nutritionix.whishlist.exception.ResourceNotFoundException;
import com.nutritionix.whishlist.model.WhishList;
import com.nutritionix.whishlist.repo.WhishListRepo;

@Service
public class WhishListServiceImpl implements WhishListService {

	@Autowired
	WhishListRepo whishListRepo;

	@Override
	@Cacheable(value = "whishlistCache", key = "#userId")
	public List<WhishList> getWhishlistByUserId(String userId) {
		List<WhishList> whishlist = whishListRepo.findByUserId(userId);
		if (whishlist.isEmpty()) {
			throw new ResourceNotFoundException("item with given id not found");
		}
		return whishlist;
	}

	@Override
	public ResponseEntity<?> addFoodItemToWhishlist(WhishList whishlist) {
		if (whishListRepo.existsByTagId(whishlist.getTagId())) {
			throw new ResourceAlreadyExistsException("item in whishlist already exists");
		}
		return new ResponseEntity<>(whishListRepo.save(whishlist), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> deleteFromWhishList(Long id) {
		Optional<WhishList> whishlist = whishListRepo.findById(id);
		if (whishlist.isEmpty()) {
			throw new ResourceNotFoundException("item with given id not found");
		}
		whishListRepo.deleteById(id);
		return new ResponseEntity<>("item deleted successfully", HttpStatus.OK);
	}

}
