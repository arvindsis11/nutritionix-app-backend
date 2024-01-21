package com.nutritionix.userprofile.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritionix.userprofile.controller.UserProfileController;
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

@ContextConfiguration(classes = {UserProfileController.class})
@ExtendWith(SpringExtension.class)
class UserProfileControllerTest {

    @MockBean
    private JsonKafkaProducer jsonKafkaProducer;

    @Autowired
    private UserProfileController userProfileController;

    @MockBean
    private UserProfileService userProfileService;

    /**
     * Method under test: {@link UserProfileController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() throws Exception {
        when(userProfileService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1.0/userProfile");
        MockMvcBuilders.standaloneSetup(userProfileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserProfileController#getUserProfileById(long)}
     */
    @Test
    void testGetUserProfileById() throws Exception {
        when(userProfileService.getUserProfileById(anyLong())).thenReturn(new UserProfileDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1.0/userProfile/{id}", 1L);
        MockMvcBuilders.standaloneSetup(userProfileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"username\":null,\"email\":null,\"firstName\":null,\"lastName\":null,\"number\":0,\"dateOfBirth\":null,"
                                        + "\"roles\":null,\"securityQuestion\":null,\"securityAnswer\":null}"));
    }

    /**
     * Method under test: {@link UserProfileController#getUserProfileById(long)}
     */
    @Test
    void testGetUserProfileById2() throws Exception {
        when(userProfileService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userProfileService.getUserProfileById(anyLong())).thenReturn(new UserProfileDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1.0/userProfile/{id}", "",
                "Uri Variables");
        MockMvcBuilders.standaloneSetup(userProfileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserProfileController#saveUserProfile(UserProfile)}
     */
    @Test
    void testSaveUserProfile() throws Exception {
        when(userProfileService.saveUserProfile(Mockito.<UserProfile>any())).thenReturn(new UserProfileDto());
        doNothing().when(jsonKafkaProducer).sendMessage(Mockito.<UserProfile>any());

        UserProfile userProfile = new UserProfile();
        userProfile.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userProfile.setEmail("jane.doe@example.org");
        userProfile.setFirstName("Jane");
        userProfile.setId(1L);
        userProfile.setLastName("Doe");
        userProfile.setNumber(1L);
        userProfile.setPassword("iloveyou");
        userProfile.setRoles(new HashSet<>());
        userProfile.setSecurityAnswer("Security Answer");
        userProfile.setSecurityQuestion("Security Question");
        userProfile.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userProfile);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1.0/userProfile/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userProfileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"username\":null,\"email\":null,\"firstName\":null,\"lastName\":null,\"number\":0,\"dateOfBirth\":null,"
                                        + "\"roles\":null,\"securityQuestion\":null,\"securityAnswer\":null}"));
    }

    /**
     * Method under test:
     * {@link UserProfileController#updateUserProfile(UserProfileDto, long)}
     */
    @Test
    void testUpdateUserProfile() throws Exception {
        when(userProfileService.updateUserProfile(Mockito.<UserProfileDto>any(), anyLong()))
                .thenReturn(new UserProfileDto());

        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setId(1L);
        userProfileDto.setRoles(new HashSet<>());
        userProfileDto.setSecurityAnswer("Security Answer");
        userProfileDto.setSecurityQuestion("Security Question");
        userProfileDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userProfileDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1.0/userProfile/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userProfileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"username\":null,\"email\":null,\"firstName\":null,\"lastName\":null,\"number\":0,\"dateOfBirth\":null,"
                                        + "\"roles\":null,\"securityQuestion\":null,\"securityAnswer\":null}"));
    }

    /**
     * Method under test: {@link UserProfileController#getUserProfileByName(String)}
     */
    @Test
    void testGetUserProfileByName() throws Exception {
        UserProfile userProfile = new UserProfile();
        userProfile.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userProfile.setEmail("jane.doe@example.org");
        userProfile.setFirstName("Jane");
        userProfile.setId(1L);
        userProfile.setLastName("Doe");
        userProfile.setNumber(1L);
        userProfile.setPassword("iloveyou");
        userProfile.setRoles(new HashSet<>());
        userProfile.setSecurityAnswer("Security Answer");
        userProfile.setSecurityQuestion("Security Question");
        userProfile.setUsername("janedoe");
        Optional<UserProfile> ofResult = Optional.of(userProfile);
        when(userProfileService.getUserByUsername(Mockito.<String>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1.0/userProfile/getProfile/{username}", "janedoe");
        MockMvcBuilders.standaloneSetup(userProfileController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"username\":\"janedoe\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"number\":1,\"dateOfBirth\":0,\"email\":"
                                        + "\"jane.doe@example.org\",\"roles\":[],\"password\":\"iloveyou\",\"securityQuestion\":\"Security Question\","
                                        + "\"securityAnswer\":\"Security Answer\"}"));
    }
}
