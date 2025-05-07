package com.ocean.alpha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ocean.alpha.domain.AlphaResponse;

@RestController
@RequestMapping("task")
public class TaskController {

	@PostMapping("create-task")
	public ResponseEntity<AlphaResponse> createTask(@RequestParam String TaskStr) {

		return null;
	}
	
	@PostMapping("update-task")
	public ResponseEntity<AlphaResponse> updateTask(@RequestParam String TaskStr) {

		return null;
	}
	
	@PostMapping("get-Tasks")
	public ResponseEntity<AlphaResponse> getTasks(@RequestParam String taskSearchStr) {

		return null;
	}
}
