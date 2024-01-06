package com.nutritionix.userprofile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nutritionix.userprofile.dto.UserProfileDto;
import com.nutritionix.userprofile.model.UserProfile;

@Repository
public interface UserProfileService {
	List<UserProfileDto> getAllUsers();

	UserProfileDto getUserProfileById(long id);

	UserProfileDto saveUserProfile(UserProfile userProfile);

	UserProfileDto updateUserProfile(UserProfileDto userProfileDto, long id);
	
	public Optional<UserProfile> getUserByUsername(String username);
}
