package com.nutritionix.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutritionix.authservice.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}