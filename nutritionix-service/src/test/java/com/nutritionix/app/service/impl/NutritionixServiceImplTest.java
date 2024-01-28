package com.nutritionix.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nutritionix.app.model.FullNutritionResponse;
import com.nutritionix.app.model.NutritionixApiResponse;
import com.nutritionix.app.response.FallbackResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {NutritionixServiceImpl.class})
@ExtendWith(SpringExtension.class)
class NutritionixServiceImplTest {
    @Autowired
    private NutritionixServiceImpl nutritionixServiceImpl;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testGetCommonFoodItems() throws RestClientException {
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(new NutritionixApiResponse());
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);
        ResponseEntity<?> actualCommonFoodItems = nutritionixServiceImpl.getCommonFoodItems("Query");
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class));
        assertNull(actualCommonFoodItems.getBody());
        assertEquals(202, actualCommonFoodItems.getStatusCodeValue());
        assertTrue(actualCommonFoodItems.getHeaders().isEmpty());
    }

    @Test
    void testGetFoodNutritions() throws RestClientException {
        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(new FullNutritionResponse());
        when(restTemplate.exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class))).thenReturn(responseEntity);
        ResponseEntity<?> actualFoodNutritions = nutritionixServiceImpl.getFoodNutritions("Food Name");
        verify(responseEntity).getBody();
        verify(restTemplate).exchange(Mockito.<String>any(), Mockito.<HttpMethod>any(), Mockito.<HttpEntity<Object>>any(),
                Mockito.<Class<Object>>any(), isA(Object[].class));
        assertEquals(200, actualFoodNutritions.getStatusCodeValue());
        assertTrue(actualFoodNutritions.hasBody());
        assertTrue(actualFoodNutritions.getHeaders().isEmpty());
    }

    @Test
    void testGetFallbackResponse() {
        ResponseEntity<?> actualFallbackResponse = nutritionixServiceImpl.getFallbackResponse("Msg", new Exception("foo"));
        assertEquals("The Nutritionix API is currently unavailable",
                ((FallbackResponse) actualFallbackResponse.getBody()).getMessage());
        assertEquals("foo", ((FallbackResponse) actualFallbackResponse.getBody()).getError());
        assertEquals(503, actualFallbackResponse.getStatusCodeValue());
        assertTrue(actualFallbackResponse.hasBody());
        assertTrue(actualFallbackResponse.getHeaders().isEmpty());
    }
}
