package com.nutritionix.userprofile.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutritionix.userprofile.model.UserProfile;

public interface UserProfileRepo extends JpaRepository<UserProfile, Long>{
	boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    
    Optional<UserProfile> findByUsername(String username);
}
