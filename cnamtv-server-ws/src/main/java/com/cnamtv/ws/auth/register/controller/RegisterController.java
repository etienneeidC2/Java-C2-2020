package com.cnamtv.ws.auth.register.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class RegisterController {

	@GetMapping
	public String getUser() {
		return "Get user was called";
	}
	
	@PostMapping
	public String createUser() {
		return "Create USer was called";
	}
	
	@PutMapping
	public String updateUser() {
		return "Update USer was called";
	}
}
