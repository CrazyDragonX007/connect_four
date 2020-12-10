package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="games")
public class games {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	int id;
	
	@Column(name="winner")
	int winner;

	public games(int winner) {
		this.winner = winner;
	}

	public int getId() {
		return id;
	}
	
	public games() {
		
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "games [id=" + id + ", winner=" + winner + "]";
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

}
