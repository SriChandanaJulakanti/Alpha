package com.ocean.alpha.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import com.ocean.alpha.domain.AlphaResponse;
import com.ocean.alpha.domain.AlphaResponse.Status;
import com.ocean.alpha.exception.AlphaException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class AlphaExceptionHandler {
	
	public ResponseEntity<AlphaResponse> alphaExceptionHandler(HttpServletRequest httpServletRequest, AlphaException exception){
		log.error(exception.getMessage(), exception);
		return getResponse(exception.getMessage(), Status.ERROR);	
	}
	
	private ResponseEntity<AlphaResponse> getResponse(String message, Status statusCode ) {
		return new ResponseEntity<>(new AlphaResponse(statusCode,message), HttpStatus.OK);		
	}
}
