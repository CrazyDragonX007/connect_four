package com.example.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	
	@GetMapping("/new")
	public String newGame() {
		return "auth token";
		
	}
	@PostMapping("/turn")
	public String turn() {
		return "turn";	
	}
}
