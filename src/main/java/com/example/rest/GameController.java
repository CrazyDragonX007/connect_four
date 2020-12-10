package com.example.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.games;
import com.example.entity.turn;
import com.example.rest.repo.GameRepo;
import com.example.rest.repo.TurnRepo;

@RestController
public class GameController {
	
	 private static final char[] PLAYERS = {'R', 'B'};
	  // dimensions for our board
	  private int width, height;
	  // grid for the board
	  private char[][] grid;
	  // we store last move made by a player
	  private int lastCol = -1, lastTop = -1;
	  private int player=0;private int moves;
	  private int gameid;
	  GameController board;
	  
	  @Autowired
	  private GameRepo gameRepo;
	  
	  @Autowired
	  private TurnRepo turnRepo;

	  public GameController(int w, int h) {
	    width = w;
	    height = h;
	    moves=w*h;
	    grid = new char[h][];

	    // init the grid will blank cell
	    for (int i = 0; i < h; i++) {
	      Arrays.fill(grid[i] = new char[w], '.');
	    }
	  }
	  public GameController() {
		}
	
	  public String horizontal() {
		    return new String(grid[lastTop]);
		  }

		  // get string representation for the col containing 
		  // the last play of the user
		  public String vertical() {
		    StringBuilder sb = new StringBuilder(height);

		    for (int h = 0; h < height; h++) {
		      sb.append(grid[h][lastCol]);
		    }

		    return sb.toString();
		  }

		  // get string representation of the "/" diagonal 
		  // containing the last play of the user
		  public String slashDiagonal() {
		    StringBuilder sb = new StringBuilder(height);

		    for (int h = 0; h < height; h++) {
		      int w = lastCol + lastTop - h;

		      if (0 <= w && w < width) {
		        sb.append(grid[h][w]);
		      }
		    }

		    return sb.toString();
		  }

		  // get string representation of the "\" 
		  // diagonal containing the last play of the user
		  public String backslashDiagonal() {
		    StringBuilder sb = new StringBuilder(height);

		    for (int h = 0; h < height; h++) {
		      int w = lastCol - lastTop + h;

		      if (0 <= w && w < width) {
		        sb.append(grid[h][w]);
		      }
		    }

		    return sb.toString();
		  }

		  // static method checking if a substring is in str
		  public static boolean contains(String str, String substring) {
		    return str.indexOf(substring) >= 0;
		  }

		  // now, we create a method checking if last play is a winning play
		  public boolean isWinningPlay() {
		    if (lastCol == -1) {
		      System.err.println("No move has been made yet");
		      return false;
		    }

		    char sym = grid[lastTop][lastCol];
		    // winning streak with the last play symbol
		    String streak = String.format("%c%c%c%c", sym, sym, sym, sym);

		    // check if streak is in row, col, 
		    // diagonal or backslash diagonal
		    return contains(horizontal(), streak) || 
		           contains(vertical(), streak) || 
		           contains(slashDiagonal(), streak) || 
		           contains(backslashDiagonal(), streak);
		  }

		  // prompts the user for a column, repeating until a valid choice is made
		  public void chooseAndDrop(char symbol, int input) {
		      System.out.println("\nPlayer " + symbol + " turn: ");
		      int col = input;

		      // check if column is ok
		      if (!(0 <= col && col < width)) {
		        System.out.println("Column must be between 0 and " + (width - 1));
		      }

		      // now we can place the symbol to the first 
		      // available row in the asked column
		      for (int h = height - 1; h >= 0; h--) {
		        if (grid[h][col] == '.') {
		          grid[lastTop = h][lastCol = col] = symbol;
		          return;
		        }
		      }

		      // if column is full ==> we need to ask for a new input
		      System.out.println("Column " + col + " is full.");
		  }
	
	@GetMapping("/new")
	public String newGame() {
		
		int height = 6; int width = 8;
		GameController newBoard=new GameController(width,height);
		board=newBoard;
		games g=new games();
		g.setWinner(0);
		gameRepo.save(g);
		gameid=g.getId();
		System.out.println(gameid);
		return "auth token";	
	}
	
	@PostMapping("/turn")
	public String turn(@RequestBody Map<String, Integer> payload) {
		//proceed only if token
		int user=(payload.get("user"));
		char symbol = PLAYERS[user];
		String s="";
		if(user!=player) {
			int x=payload.get("move");
			System.out.println(x);
			board.chooseAndDrop(symbol, x);
			player=1-player;
			s+=Arrays.deepToString(board.grid);
			//save move in db
			turn m=new turn();
			m.setMove(s);
			m.setPlayer(user);
			m.setGameid(gameid);
			turnRepo.save(m);
			
		}else {
			return "wrong player";
		}
		boolean x=board.isWinningPlay();
		if (x) {
	          System.out.println("\nPlayer " + symbol + " wins!");
	          //expire token
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
	public List<turn> listTurns(){
		//System.out.println("1");
		return turnRepo.findAll();
	}
	
}
