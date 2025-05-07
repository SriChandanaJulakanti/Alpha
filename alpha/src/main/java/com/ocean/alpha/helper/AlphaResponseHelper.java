package com.ocean.alpha.helper;

import com.ocean.alpha.domain.AlphaResponse;
import com.ocean.alpha.domain.AlphaResponse.Status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class AlphaResponseHelper {

	private AlphaResponseHelper() {
	}

	public static ResponseEntity<AlphaResponse> alphaResponseHelper(Status status, String message, Object response) {
		return getResponse(status, message, response, HttpStatus.OK);
	}

	public static ResponseEntity<AlphaResponse> getResponse(Status status, String message, Object response, HttpStatus httpStatus) {
		AlphaResponse alphaWebResponse = new AlphaResponse(status, message, response);
		return new ResponseEntity<>(alphaWebResponse, httpStatus);
	}
}

