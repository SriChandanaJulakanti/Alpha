package com.ocean.alpha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ocean.alpha.domain.AlphaResponse;
import com.ocean.alpha.domain.AlphaResponse.Status;
import com.ocean.alpha.domain.Epic;
import com.ocean.alpha.exception.AlphaException;
import com.ocean.alpha.service.EpicServiceImpl;

@RestController
@RequestMapping("epic")
public class EpicController {

	@Autowired
	EpicServiceImpl epicServiceImpl;
	
	@PostMapping("create-epic")
	public ResponseEntity<AlphaResponse> createEpic(@RequestBody Epic epic) throws AlphaException {		
		epicServiceImpl.createEpic(epic);
		return new ResponseEntity<>(new AlphaResponse(Status.SUCCESS,"Epic created successfully"),HttpStatus.OK);
	}

	@PostMapping("update-epic")
	public ResponseEntity<AlphaResponse> updateEpic(@RequestParam String epicStr) {

		return null;
	}

	@PostMapping("get-epics")
	public ResponseEntity<AlphaResponse> getEpics(@RequestParam String epicSearchStr) {

		return null;
	}
	
}
