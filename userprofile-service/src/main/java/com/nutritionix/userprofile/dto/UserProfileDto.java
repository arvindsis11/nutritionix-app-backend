package com.nutritionix.userprofile.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileDto {
	private long id;
	private String username;
	private String email;
	private Set<String> roles;
	private String securityQuestion;
	private String securityAnswer;

}