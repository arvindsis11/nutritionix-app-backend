package com.nutritionix.app.response;


public class FallbackResponse {
	private String message;
	private String error;
	
	public FallbackResponse() {
		
	}

	public FallbackResponse(String message, String error) {
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	
}
