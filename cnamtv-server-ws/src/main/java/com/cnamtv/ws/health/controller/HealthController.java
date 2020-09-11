package com.cnamtv.ws.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health") //http://localhost:8080/health
public class HealthController {

	@GetMapping
	public String getServerStatus() {
		return "The server is healthy";
	}
	
}
