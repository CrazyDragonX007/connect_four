package com.example.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.ConnectFour.JWT;
import com.example.ConnectFour.Utility;
import com.example.entity.Games;
import com.example.entity.Turn;
import com.example.rest.repo.GameRepo;
import com.example.rest.repo.TurnRepo;

import io.jsonwebtoken.Claims;

@RestController
public class GameController {

	private int gameid, player;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private GameService board;

	@Autowired
	private GameRepo gameRepo;

	@Autowired
	private TurnRepo turnRepo;

	int moves;

	@GetMapping("/new")
	public String newGame() {

		int height = 6;
		int width = 8;
		board = new GameService(width, height);
		moves = board.getMoves();
		player = board.getPlayer();
		Games game = new Games();
		game.setWinner(0);
		game.setExpired(false);
		gameRepo.save(game);
		gameid = game.getId();
		System.out.println(gameid);
		String jwt = JWT.jwt(gameid,"");
		game.setToken(jwt);
		gameRepo.save(game);
		return jwt;
	}

	@PostMapping("/turn")
	public String turn(@RequestBody Map<String,Integer> payload, @RequestHeader("Auth") String jwt) {
		//board = new GameService(6,8);
		Claims cls = JWT.decodeJWT(jwt);
		int gameId = Integer.parseInt((String) cls.get("sub"));
		//System.out.println(gameId);
		Games game = gameRepo.findById(gameId).orElseThrow();
		// proceed only if token not expired
		if (game.getExpired()) {
			return "Invalid game";
		}
		Session session = sessionFactory.openSession();
		String queryString="from Turn where gameid="+gameId+" order by turnid DESC";
		Query query=session.createQuery(queryString);
		List result=query.getResultList();
		if(board.getMoves() == 0) {
			//System.out.println("1");
			board = new GameService(8,6);}
		if(!(result.isEmpty())) {
			Turn turn=(Turn) result.get(0);
			String move=turn.getMove();
			char grid[][]=Utility.stringToDeep(move);
			board.setGrid(grid);
		}
		moves--;
		// System.out.println(moves);
		if (moves == 0) {
			game.setExpired(true);
			gameRepo.save(game);
			return "Draw";
		}
		// int user = (payload.get("user"));
		// GameService gs=new GameService();
		char symbol = GameService.getPlayer(player);
		String state = "";
		
		// if (user != player) {
		int move = payload.get("move");
		// System.out.println(move);
		String res=board.chooseAndDrop(symbol, move);
		if(res!="true")
			return res;
		state += Arrays.deepToString(board.getGrid());
		// save move in db
		//System.out.println(state);
		Turn turn = new Turn();
		turn.setMove(state);
		turn.setPlayer(player);
		turn.setGameid(gameId);
		turnRepo.save(turn);
		player = 1 - player;
		// } else {
		// moves++;
		// return "wrong player";
		// }
		boolean won = board.isWinningPlay();
		if (won) {
			// System.out.println("\nPlayer " + symbol + " wins!");
			// System.out.println(g);
			game.setExpired(true);
			if (symbol == 'R')
				game.setWinner(1);
			else
				game.setWinner(2);
			gameRepo.save(game);
			return symbol+" won";
		}
		return "Success";
	}

	@GetMapping("/list")
	public List<Games> listGames() {
		System.out.println("1");
		return gameRepo.findAll();
	}

}
