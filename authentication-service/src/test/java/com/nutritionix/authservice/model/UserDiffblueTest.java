package com.nutritionix.authservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class UserTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User()}
     *   <li>{@link User#setEmail(String)}
     *   <li>{@link User#setId(Long)}
     *   <li>{@link User#setPassword(String)}
     *   <li>{@link User#setRoles(Set)}
     *   <li>{@link User#setSecurityAnswer(String)}
     *   <li>{@link User#setSecurityQuestion(String)}
     *   <li>{@link User#setUsername(String)}
     *   <li>{@link User#getEmail()}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getRoles()}
     *   <li>{@link User#getSecurityAnswer()}
     *   <li>{@link User#getSecurityQuestion()}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor() {
        User actualUser = new User();
        actualUser.setEmail("jane.doe@example.org");
        actualUser.setId(1L);
        actualUser.setPassword("iloveyou");
        HashSet<Role> roles = new HashSet<>();
        actualUser.setRoles(roles);
        actualUser.setSecurityAnswer("Security Answer");
        actualUser.setSecurityQuestion("Security Question");
        actualUser.setUsername("janedoe");
        String actualEmail = actualUser.getEmail();
        Long actualId = actualUser.getId();
        String actualPassword = actualUser.getPassword();
        Set<Role> actualRoles = actualUser.getRoles();
        String actualSecurityAnswer = actualUser.getSecurityAnswer();
        String actualSecurityQuestion = actualUser.getSecurityQuestion();
        assertEquals("Security Answer", actualSecurityAnswer);
        assertEquals("Security Question", actualSecurityQuestion);
        assertEquals("iloveyou", actualPassword);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals("janedoe", actualUser.getUsername());
        assertEquals(1L, actualId.longValue());
        assertSame(roles, actualRoles);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User(String, String, String, String, String)}
     *   <li>{@link User#setEmail(String)}
     *   <li>{@link User#setId(Long)}
     *   <li>{@link User#setPassword(String)}
     *   <li>{@link User#setRoles(Set)}
     *   <li>{@link User#setSecurityAnswer(String)}
     *   <li>{@link User#setSecurityQuestion(String)}
     *   <li>{@link User#setUsername(String)}
     *   <li>{@link User#getEmail()}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getRoles()}
     *   <li>{@link User#getSecurityAnswer()}
     *   <li>{@link User#getSecurityQuestion()}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        User actualUser = new User("janedoe", "jane.doe@example.org", "iloveyou", "Security Question", "Security Answer");
        actualUser.setEmail("jane.doe@example.org");
        actualUser.setId(1L);
        actualUser.setPassword("iloveyou");
        HashSet<Role> roles = new HashSet<>();
        actualUser.setRoles(roles);
        actualUser.setSecurityAnswer("Security Answer");
        actualUser.setSecurityQuestion("Security Question");
        actualUser.setUsername("janedoe");
        String actualEmail = actualUser.getEmail();
        Long actualId = actualUser.getId();
        String actualPassword = actualUser.getPassword();
        Set<Role> actualRoles = actualUser.getRoles();
        String actualSecurityAnswer = actualUser.getSecurityAnswer();
        String actualSecurityQuestion = actualUser.getSecurityQuestion();
        assertEquals("Security Answer", actualSecurityAnswer);
        assertEquals("Security Question", actualSecurityQuestion);
        assertEquals("iloveyou", actualPassword);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals("janedoe", actualUser.getUsername());
        assertEquals(1L, actualId.longValue());
        assertSame(roles, actualRoles);
    }
}
