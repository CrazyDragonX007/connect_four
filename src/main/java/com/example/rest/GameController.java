package com.example.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ConnectFour.JWT;
import com.example.entity.games;
import com.example.entity.turn;
import com.example.rest.repo.GameRepo;
import com.example.rest.repo.TurnRepo;

import io.jsonwebtoken.Claims;

@RestController
public class GameController {
	
	
	  private int gameid,player;
	  
	  @Autowired
	  private GameService board;
	  
	  @Autowired
	  private GameRepo gameRepo;
	  
	  @Autowired
	  private TurnRepo turnRepo;
	  
	  @Autowired
	  private EntityManager entityManager;
	  int moves;
	
	@GetMapping("/new")
	public String newGame() {
		
		int height = 6; int width = 8;
		board=new GameService(width,height);
		moves=board.getMoves();
		player=board.getPlayer();
		games g=new games();
		g.setWinner(0);
		gameRepo.save(g);
		gameid=g.getId();
		//System.out.println(gameid);
		String jwt=JWT.jwt(gameid);
		return jwt;	
	}
	
	@PostMapping("/turn")
	public String turn(@RequestBody Map<String, Integer> payload, @RequestHeader("Authorization") String jwt) {
		//proceed only if token
		Claims cls=JWT.decodeJWT(jwt);
		int c=Integer.parseInt((String) cls.get("sub"));
		if(c!=gameid)
			return "wrong game";
		moves--;
		//System.out.println(moves);
		if(moves==0)
			return "Draw";
		int user=(payload.get("user"));
		//GameService gs=new GameService();
		char symbol = GameService.getPlayer(user);
		String s="";
		if(user!=player) {
			int x=payload.get("move");
			System.out.println(x);
			board.chooseAndDrop(symbol, x);
			player=1-player;
			s+=Arrays.deepToString(board.getGrid());
			//save move in db
			turn m=new turn();
			m.setMove(s);
			m.setPlayer(user);
			m.setGameid(gameid);
			turnRepo.save(m);	
		}else {
			moves++;
			return "wrong player";
		}
		boolean x=board.isWinningPlay();
		if (x) {
	          //System.out.println("\nPlayer " + symbol + " wins!");
	          games g= gameRepo.findById(gameid).orElseThrow();
	         // System.out.println(g);
	          if(symbol=='R')
	        	  g.setWinner(1);
	          else
	        	  g.setWinner(2);
	          gameRepo.save(g);
	        }
		return s;	
	}
	
	@GetMapping("/list")
	public List<games> listGames(){
		//System.out.println("1");
		return gameRepo.findAll();
	}
	
	@GetMapping("/moves")
	public List<turn> listTurns(@RequestParam int id){
		//System.out.println("1");
		Session cs=entityManager.unwrap(Session.class);
		Query<turn> q=cs.createQuery("from turn where gameid="+id,turn.class);
		List<turn>  tr=q.getResultList();
		return tr;
	}
	
}
