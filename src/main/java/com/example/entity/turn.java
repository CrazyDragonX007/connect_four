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
	
	public turn(int turnid, int player, int gameid) {
		this.turnid = turnid;
		this.player = player;
		this.gameid = gameid;
	}

	@Override
	public String toString() {
		return "turn [turnid=" + turnid + ", player=" + player + ", gameid=" + gameid + "]";
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

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int turnid;
	
	@Column(name="player")
	int player;
	
	@Column(name="gameid")
	int gameid;
	
}
