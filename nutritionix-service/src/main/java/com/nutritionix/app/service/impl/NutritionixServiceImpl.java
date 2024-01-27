package com.nutritionix.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nutritionix.app.model.FullNutritionResponse;
import com.nutritionix.app.model.NutritionixApiResponse;
import com.nutritionix.app.response.FallbackResponse;
import com.nutritionix.app.service.NutritionixService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class NutritionixServiceImpl implements NutritionixService {

	@Value("${nutritionix.api.url}")
	private String apiUrl;

	@Value("${nutritionix.api.appId}")
	private String appId;

	@Value("${nutritionix.api.appKey}")
	private String appKey;

	private final RestTemplate restTemplate;

	@Autowired
	public NutritionixServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	@CircuitBreaker(name = "NUTRITION-SERVICE", fallbackMethod = "getFallbackResponse")
	public ResponseEntity<?> getCommonFoodItems(String query) {
		String url = apiUrl + "/search/instant/?query=" + query;//change something here to see circuit breaker

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-app-id", appId);
		headers.set("x-app-key", appKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<NutritionixApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
				NutritionixApiResponse.class);

		return new ResponseEntity<>(response.getBody().getCommon(), HttpStatus.ACCEPTED);
	}

	@Override
	@CircuitBreaker(name = "NUTRITION-SERVICE", fallbackMethod = "getFallbackResponse")
	public ResponseEntity<?> getFoodNutritions(String foodName) {
		String url = apiUrl + "/natural/nutrients";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-app-id", appId);
		headers.set("x-app-key", appKey);

		// supports natural language type query -like 10 grapes
		String queryParam = "{\"query\": \"" + foodName + "\"}";

		HttpEntity<String> requestEntity = new HttpEntity<>(queryParam, headers);

		ResponseEntity<FullNutritionResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				requestEntity, FullNutritionResponse.class);

		return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> getFallbackResponse(String msg,Exception e) {
		FallbackResponse response = new FallbackResponse("The Nutritionix API is currently unavailable", e.getMessage());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
	}

}
