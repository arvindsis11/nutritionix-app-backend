package com.nutritionix.whishlist.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nutritionix.whishlist.model.WhishList;

@Repository
public interface WhishListRepo extends JpaRepository<WhishList, Long> {

	List<WhishList> findByUserId(String userId);

	Optional<WhishList> findByTagId(String tagId);

	boolean existsByTagId(String tagId);
}