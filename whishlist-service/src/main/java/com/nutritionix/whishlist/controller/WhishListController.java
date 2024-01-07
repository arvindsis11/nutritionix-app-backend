package com.nutritionix.whishlist.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.whishlist.exception.ExternalServiceException;
import com.nutritionix.whishlist.exception.InvalidCredentialsException;
import com.nutritionix.whishlist.feign.AuthClient;
import com.nutritionix.whishlist.model.WhishList;
import com.nutritionix.whishlist.service.WhishListService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/v1.0/wishlist")
@CrossOrigin("*")
public class WhishListController {
	@Autowired
	WhishListService whishListService;

	@Autowired
	AuthClient authClient;

	@GetMapping("/getByUserId/{userId}")
	@Operation(summary = "get whishlist by userId")
	public ResponseEntity<?> getWhishlistByUserId(@PathVariable String userId,
			@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
		try {
			Map<String, String> userInfo = (Map<String, String>) authClient.validateToken(token).getBody();
			if (userInfo.containsValue("ROLE_ADMIN") || userInfo.containsValue("ROLE_CUSTOMER")) {
				return new ResponseEntity<>(whishListService.getWhishlistByUserId(userId), HttpStatus.OK);
			} else {
				throw new InvalidCredentialsException("Access Denied");
			}
		} catch (FeignException e) {
			throw new ExternalServiceException(e.getMessage());
		}
	}

	@PostMapping("/addItem")
	@Operation(summary = "add item to the whishlist")
	public ResponseEntity<?> addStockToWhishlist(@RequestBody WhishList whishlist,
			@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
		try {
			Map<String, String> userInfo = (Map<String, String>) authClient.validateToken(token).getBody();
			if (userInfo.containsValue("ROLE_ADMIN") || userInfo.containsValue("ROLE_CUSTOMER")) {
				String userId = userInfo.keySet().iterator().next();
				System.out.println("userIdv " + userId);
				whishlist.setUserId(userId);
				return new ResponseEntity<>(whishListService.addFoodItemToWhishlist(whishlist), HttpStatus.OK);
			} else {
				throw new InvalidCredentialsException("Access Denied");
			}
		} catch (FeignException e) {
			throw new ExternalServiceException(e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	@Operation(summary = "delete items from the whishlist")
	public ResponseEntity<?> deleteFromWhishList(@PathVariable Long id,
			@Parameter(hidden = true) @RequestHeader("Authorization") String token) {
		try {
			Map<String, String> userInfo = (Map<String, String>) authClient.validateToken(token).getBody();
			if (userInfo.containsValue("ROLE_ADMIN") || userInfo.containsValue("ROLE_CUSTOMER")) {
				return new ResponseEntity<>(whishListService.deleteFromWhishList(id), HttpStatus.OK);
			} else {
				throw new InvalidCredentialsException("Access Denied");
			}
		} catch (FeignException e) {
			throw new ExternalServiceException(e.getMessage());
		}
	}
}
