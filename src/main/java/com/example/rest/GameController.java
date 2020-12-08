package com.example.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.utils.ConnectFour;

@RestController
public class GameController {
	
	int height = 6; int width = 8; int moves = height * width;
	ConnectFour board = new ConnectFour(width, height);
	
	@GetMapping("/new")
	public String newGame() {
		return "auth token";	
	}
	
	@PostMapping("/turn")
	public int turn(@RequestParam("player") int player, @RequestParam("move") int move) {
		return player+move;	
	}
	
	@GetMapping("/list")
	public String gamesList() {
		return "games";
	}
	
	@GetMapping("/moves")
	public String Moves() {
		return "moves";
	}
	
}
