package com.example.rest;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	
	 private static final char[] PLAYERS = {'R', 'B'};
	  // dimensions for our board
	  private int width, height;
	  // grid for the board
	  private char[][] grid;
	  // we store last move made by a player
	  private int lastCol = -1, lastTop = -1;
	  private int player=0;
	  GameController board;
	  
	  

	  public GameController(int w, int h) {
	    width = w;
	    height = h;
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

		  // get string representation fo the col containing 
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
		        //continue;
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
		
		int height = 6; int width = 8; int moves = height * width;
		GameController newBoard=new GameController(width,height);
		board=newBoard;
		return "auth token";	
	}
	
	@PostMapping("/turn")
	public boolean turn(@RequestBody Map<String, Integer> payload) {
		//proceed only if token
		int user=(payload.get("user"));
		char symbol = PLAYERS[user];
		if(user!=player) {
			int x=payload.get("move");
			System.out.println(x);
			board.chooseAndDrop(symbol, x);
			player=1-player;
		}
		boolean x=board.isWinningPlay();
		if (x) {
	          System.out.println("\nPlayer " + symbol + " wins!");
	        }
		return x;	
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
