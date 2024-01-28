package com.nutritionix.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nutritionix.app.exception.ExternalServiceException;
import com.nutritionix.app.exception.InvalidCredentialsException;
import com.nutritionix.app.feign.AuthClient;
import com.nutritionix.app.service.NutritionixService;

import feign.FeignException;

public class NutritionixControllerTest {

    private NutritionixService nutritionixService;
    private AuthClient authClient;
    private NutritionixController controller;

    @BeforeEach
    void setUp() {
        nutritionixService = mock(NutritionixService.class);
        authClient = mock(AuthClient.class);
        controller = new NutritionixController(nutritionixService, authClient);
    }

    @Test
    void testGetCommonFoodItems_ValidInputAndAuthorization() {
        // Mock AuthClient to return a valid response
    	Map hash =  new HashMap<>();
    	hash.put("ROLE_ADMIN", "ROLE_ADMIN");
        when(authClient.validateToken(anyString())).thenReturn(ResponseEntity.ok().body(hash));

        // Mock NutritionixService to return some common food items
        when(nutritionixService.getCommonFoodItems(anyString())).thenReturn(ResponseEntity.ok().body(null));

        ResponseEntity<?> response = controller.getCommonFoodItems("apple", "validToken");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Assert other expectations on the response body if needed
    }

    @Test
    void testGetCommonFoodItems_InvalidAuthorization() {
        // Mock AuthClient to return an unauthorized response
        when(authClient.validateToken(anyString())).thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

        assertThrows(NullPointerException.class, () -> {
            controller.getCommonFoodItems("apple", "invalidToken");
        });
    }

    @Test
    void testGetCommonFoodItems_ExternalServiceFailure() {
        // Mock AuthClient to throw FeignException
        when(authClient.validateToken(anyString())).thenThrow(FeignException.class);

        assertThrows(ExternalServiceException.class, () -> {
            controller.getCommonFoodItems("apple", "validToken");
        });
    }


}
