package com.nutritionix.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutritionix.authservice.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
Optional<User> findByUsername(String username);

Boolean existsByUsername(String username);

Boolean existsByEmail(String email);

//@Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
//Optional<User> validateUser(String username,String password);//verify username and password
}