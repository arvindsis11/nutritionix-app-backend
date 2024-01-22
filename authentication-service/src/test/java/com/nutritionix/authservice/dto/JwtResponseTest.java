package com.nutritionix.authservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.nutritionix.authservice.model.Role;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class JwtResponseTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link JwtResponse#JwtResponse(String, Long, String, String, Set)}
     *   <li>{@link JwtResponse#setAccessToken(String)}
     *   <li>{@link JwtResponse#setEmail(String)}
     *   <li>{@link JwtResponse#setId(Long)}
     *   <li>{@link JwtResponse#setUsername(String)}
     *   <li>{@link JwtResponse#getAccessToken()}
     *   <li>{@link JwtResponse#getEmail()}
     *   <li>{@link JwtResponse#getId()}
     *   <li>{@link JwtResponse#getRoles()}
     *   <li>{@link JwtResponse#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor() {
        HashSet<Role> set = new HashSet<>();
        JwtResponse actualJwtResponse = new JwtResponse("ABC123", 1L, "janedoe", "jane.doe@example.org", set);
        actualJwtResponse.setAccessToken("ABC123");
        actualJwtResponse.setEmail("jane.doe@example.org");
        actualJwtResponse.setId(1L);
        actualJwtResponse.setUsername("janedoe");
        String actualAccessToken = actualJwtResponse.getAccessToken();
        String actualEmail = actualJwtResponse.getEmail();
        Long actualId = actualJwtResponse.getId();
        Set<Role> actualRoles = actualJwtResponse.getRoles();
        assertEquals("ABC123", actualAccessToken);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals("janedoe", actualJwtResponse.getUsername());
        assertEquals(1L, actualId.longValue());
        assertSame(set, actualRoles);
    }
}
