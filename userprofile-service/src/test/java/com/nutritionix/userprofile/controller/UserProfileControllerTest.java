package com.nutritionix.userprofile.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritionix.userprofile.dto.UserProfileDto;
import com.nutritionix.userprofile.kafka.JsonKafkaProducer;
import com.nutritionix.userprofile.model.UserProfile;
import com.nutritionix.userprofile.service.UserProfileService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = { UserProfileController.class })
@ExtendWith(SpringExtension.class)
class UserProfileControllerTest {
	@MockBean
	private JsonKafkaProducer jsonKafkaProducer;

	@Autowired
	private UserProfileController userProfileController;

	@MockBean
	private UserProfileService userProfileService;

	@Test
	void testGetAllUsers() throws Exception {
		when(userProfileService.getAllUsers()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1.0/userProfile/getAll");
		MockMvcBuilders.standaloneSetup(userProfileController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void testGetUserProfileById() throws Exception {
		when(userProfileService.getUserProfileById(anyLong())).thenReturn(new UserProfileDto());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1.0/userProfile/{id}", 1L);
		MockMvcBuilders.standaloneSetup(userProfileController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"id\":0,\"username\":null,\"email\":null,\"roles\":null,\"securityQuestion\":null,\"securityAnswer\":null}"));
	}

	@Test
	void testSaveUserProfile() throws Exception {
		when(userProfileService.saveUserProfile(Mockito.<UserProfile>any())).thenReturn(new UserProfileDto());

		UserProfile userProfile = new UserProfile();
		userProfile.setEmail("jane.doe@example.org");

		userProfile.setId(1L);

		userProfile.setPassword("password1");
		userProfile.setRoles(new HashSet<>());
		userProfile.setSecurityAnswer("Security Answer");
		userProfile.setSecurityQuestion("Security Question");
		userProfile.setUsername("janedoe");
		String content = (new ObjectMapper()).writeValueAsString(userProfile);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1.0/userProfile/add")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(userProfileController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"id\":0,\"username\":null,\"email\":null,\"roles\":null,\"securityQuestion\":null,\"securityAnswer\":null}"));
	}

	@Test
	void testUpdateUserProfile() throws Exception {
		when(userProfileService.updateUserProfile(Mockito.<UserProfileDto>any(), anyLong()))
				.thenReturn(new UserProfileDto());

		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setEmail("jane.doe@example.org");
		userProfileDto.setId(1L);
		userProfileDto.setRoles(new HashSet<>());
		userProfileDto.setSecurityAnswer("Security Answer");
		userProfileDto.setSecurityQuestion("Security Question");
		userProfileDto.setUsername("janedoe");
		String content = (new ObjectMapper()).writeValueAsString(userProfileDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1.0/userProfile/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(userProfileController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"id\":0,\"username\":null,\"email\":null,\"roles\":null,\"securityQuestion\":null,\"securityAnswer\":null}"));
	}

	@Test
	void testGetUserProfileByName() throws Exception {
		UserProfile userProfile = new UserProfile();
		userProfile.setEmail("jane.doe@example.org");
		userProfile.setId(1L);
		userProfile.setPassword("password1");
		userProfile.setRoles(new HashSet<>());
		userProfile.setSecurityAnswer("Security Answer");
		userProfile.setSecurityQuestion("Security Question");
		userProfile.setUsername("janedoe");
		Optional<UserProfile> ofResult = Optional.of(userProfile);
		when(userProfileService.getUserByUsername(Mockito.<String>any())).thenReturn(ofResult);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/v1.0/userProfile/getProfile/{username}", "janedoe");
		MockMvcBuilders.standaloneSetup(userProfileController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"username\":\"janedoe\",\"email\":"
						+ "\"jane.doe@example.org\",\"roles\":[],\"password\":\"password1\",\"securityQuestion\":\"Security Question\","
						+ "\"securityAnswer\":\"Security Answer\"}"));
	}
}
