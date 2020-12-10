package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="turn")
public class turn {

	@Override
	public String toString() {
		return "turn [turnid=" + turnid + ", player=" + player + ", gameid=" + gameid + ", move=" + move + "]";
	}

	public int getTurnid() {
		return turnid;
	}

	public void setTurnid(int turnid) {
		this.turnid = turnid;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public turn(int turnid, int player, int gameid, String move) {
		this.player = player;
		this.gameid = gameid;
		this.move = move;
	}
	
	public turn() {
		
	}

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="turnid")
	int turnid;
	
	@Column(name="player")
	int player;
	
	@Column(name="gameid")
	int gameid;
	
	@Column(name="move")
	String move;
	
}
