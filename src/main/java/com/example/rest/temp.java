package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Games;
import com.example.rest.repo.GameRepo;

@Component
public class temp {
	@Autowired
	private GameRepo gameRepo;
	
	private int gameid;
	
	public void newGame() {
		Games game = new Games();
		game.setWinner(0);
		game.setExpired(false);
		System.out.println(gameRepo);
		gameRepo.save(game);
		gameid = game.getId();
		System.out.println(gameid);
	}

}
