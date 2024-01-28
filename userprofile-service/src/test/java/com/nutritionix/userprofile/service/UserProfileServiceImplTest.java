package com.nutritionix.userprofile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nutritionix.userprofile.dto.UserProfileDto;
import com.nutritionix.userprofile.exception.ResourceAlreadyExistsException;
import com.nutritionix.userprofile.exception.ResourceNotFoundException;
import com.nutritionix.userprofile.model.UserProfile;
import com.nutritionix.userprofile.repo.UserProfileRepo;
import com.nutritionix.userprofile.service.UserProfileServiceImpl;

@ContextConfiguration(classes = {UserProfileServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserProfileServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserProfileRepo userProfileRepo;

    @Autowired
    private UserProfileServiceImpl userProfileServiceImpl;

    /**
     * Method under test: {@link UserProfileServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        when(userProfileRepo.findAll()).thenReturn(new ArrayList<>());
        List<UserProfileDto> actualAllUsers = userProfileServiceImpl.getAllUsers();
        verify(userProfileRepo).findAll();
        assertTrue(actualAllUsers.isEmpty());
    }

    /**
     * Method under test: {@link UserProfileServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("jane.doe@example.org");
        userProfile.setId(1L);
        userProfile.setPassword("iloveyou");
        userProfile.setRoles(new HashSet<>());
        userProfile.setSecurityAnswer("Security Answer");
        userProfile.setSecurityQuestion("Security Question");
        userProfile.setUsername("janedoe");

        ArrayList<UserProfile> userProfileList = new ArrayList<>();
        userProfileList.add(userProfile);
        when(userProfileRepo.findAll()).thenReturn(userProfileList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any())).thenReturn(new UserProfileDto());
        List<UserProfileDto> actualAllUsers = userProfileServiceImpl.getAllUsers();
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any());
        verify(userProfileRepo).findAll();
        assertEquals(1, actualAllUsers.size());
    }

    /**
     * Method under test: {@link UserProfileServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("jane.doe@example.org");
        userProfile.setId(1L);
        userProfile.setPassword("iloveyou");
        userProfile.setRoles(new HashSet<>());
        userProfile.setSecurityAnswer("Security Answer");
        userProfile.setSecurityQuestion("Security Question");
        userProfile.setUsername("janedoe");

        ArrayList<UserProfile> userProfileList = new ArrayList<>();
        userProfileList.add(userProfile);
        when(userProfileRepo.findAll()).thenReturn(userProfileList);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any()))
                .thenThrow(new ResourceAlreadyExistsException("An error occurred"));
        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileServiceImpl.getAllUsers());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any());
        verify(userProfileRepo).findAll();
    }

    /**
     * Method under test: {@link UserProfileServiceImpl#getUserProfileById(long)}
     */
    @Test
    void testGetUserProfileById() {
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
        when(userProfileRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserProfileDto userProfileDto = new UserProfileDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any())).thenReturn(userProfileDto);
        UserProfileDto actualUserProfileById = userProfileServiceImpl.getUserProfileById(1L);
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any());
        verify(userProfileRepo).findById(Mockito.<Long>any());
        assertSame(userProfileDto, actualUserProfileById);
    }

    /**
     * Method under test: {@link UserProfileServiceImpl#getUserProfileById(long)}
     */
    @Test
    void testGetUserProfileById2() {
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
        when(userProfileRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any()))
                .thenThrow(new ResourceAlreadyExistsException("An error occurred"));
        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileServiceImpl.getUserProfileById(1L));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any());
        verify(userProfileRepo).findById(Mockito.<Long>any());
    }

   

    /**
     * Method under test:
     * {@link UserProfileServiceImpl#saveUserProfile(UserProfile)}
     */
    @Test
    void testSaveUserProfile() {
        when(userProfileRepo.existsByUsername(Mockito.<String>any())).thenReturn(true);

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
        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileServiceImpl.saveUserProfile(userProfile));
        verify(userProfileRepo).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link UserProfileServiceImpl#saveUserProfile(UserProfile)}
     */
    @Test
    void testSaveUserProfile2() {
        when(userProfileRepo.existsByEmail(Mockito.<String>any())).thenReturn(true);
        when(userProfileRepo.existsByUsername(Mockito.<String>any())).thenReturn(false);

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
        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileServiceImpl.saveUserProfile(userProfile));
        verify(userProfileRepo).existsByEmail(Mockito.<String>any());
        verify(userProfileRepo).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link UserProfileServiceImpl#saveUserProfile(UserProfile)}
     */
    @Test
    void testSaveUserProfile3() {
        when(userProfileRepo.existsByUsername(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

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
        assertThrows(ResourceNotFoundException.class, () -> userProfileServiceImpl.saveUserProfile(userProfile));
        verify(userProfileRepo).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link UserProfileServiceImpl#updateUserProfile(UserProfileDto, long)}
     */
    @Test
    void testUpdateUserProfile() {
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

        UserProfile userProfile2 = new UserProfile();
        userProfile2.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userProfile2.setEmail("jane.doe@example.org");
        userProfile2.setFirstName("Jane");
        userProfile2.setId(1L);
        userProfile2.setLastName("Doe");
        userProfile2.setNumber(1L);
        userProfile2.setPassword("iloveyou");
        userProfile2.setRoles(new HashSet<>());
        userProfile2.setSecurityAnswer("Security Answer");
        userProfile2.setSecurityQuestion("Security Question");
        userProfile2.setUsername("janedoe");
        when(userProfileRepo.save(Mockito.<UserProfile>any())).thenReturn(userProfile2);
        when(userProfileRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserProfileDto userProfileDto = new UserProfileDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any())).thenReturn(userProfileDto);
        UserProfileDto actualUpdateUserProfileResult = userProfileServiceImpl.updateUserProfile(new UserProfileDto(), 1L);
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any());
        verify(userProfileRepo).findById(Mockito.<Long>any());
        verify(userProfileRepo).save(Mockito.<UserProfile>any());
        assertSame(userProfileDto, actualUpdateUserProfileResult);
    }

    /**
     * Method under test:
     * {@link UserProfileServiceImpl#updateUserProfile(UserProfileDto, long)}
     */
    @Test
    void testUpdateUserProfile2() {
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

        UserProfile userProfile2 = new UserProfile();
        userProfile2.setDateOfBirth(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userProfile2.setEmail("jane.doe@example.org");
        userProfile2.setFirstName("Jane");
        userProfile2.setId(1L);
        userProfile2.setLastName("Doe");
        userProfile2.setNumber(1L);
        userProfile2.setPassword("iloveyou");
        userProfile2.setRoles(new HashSet<>());
        userProfile2.setSecurityAnswer("Security Answer");
        userProfile2.setSecurityQuestion("Security Question");
        userProfile2.setUsername("janedoe");
        when(userProfileRepo.save(Mockito.<UserProfile>any())).thenReturn(userProfile2);
        when(userProfileRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any()))
                .thenThrow(new ResourceAlreadyExistsException("An error occurred"));
        assertThrows(ResourceAlreadyExistsException.class,
                () -> userProfileServiceImpl.updateUserProfile(new UserProfileDto(), 1L));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<UserProfileDto>>any());
        verify(userProfileRepo).findById(Mockito.<Long>any());
        verify(userProfileRepo).save(Mockito.<UserProfile>any());
    }

   

    /**
     * Method under test: {@link UserProfileServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername() {
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
        when(userProfileRepo.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        Optional<UserProfile> actualUserByUsername = userProfileServiceImpl.getUserByUsername("janedoe");
        verify(userProfileRepo).findByUsername(Mockito.<String>any());
        assertTrue(actualUserByUsername.isPresent());
        assertSame(ofResult, actualUserByUsername);
    }

    /**
     * Method under test: {@link UserProfileServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername2() {
        Optional<UserProfile> emptyResult = Optional.empty();
        when(userProfileRepo.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> userProfileServiceImpl.getUserByUsername("janedoe"));
        verify(userProfileRepo).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserProfileServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername3() {
        when(userProfileRepo.findByUsername(Mockito.<String>any()))
                .thenThrow(new ResourceAlreadyExistsException("An error occurred"));
        assertThrows(ResourceAlreadyExistsException.class, () -> userProfileServiceImpl.getUserByUsername("janedoe"));
        verify(userProfileRepo).findByUsername(Mockito.<String>any());
    }
}
