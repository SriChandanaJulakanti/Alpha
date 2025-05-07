package com.ocean.alpha.domain;

public class AlphaResponse {

	public enum Status {
		SUCCESS, WARNING, FAILURE, ERROR
	}
	
	private Status statusCode;
	private String message;
	private Object response;
	
	public AlphaResponse(Status statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public AlphaResponse(Status statusCode, String message, Object response) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.response = response;
	}
	
	public AlphaResponse() {
		super();
	}
}
