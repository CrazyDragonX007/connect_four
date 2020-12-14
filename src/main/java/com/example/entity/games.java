package com.example.entity;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Games {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	int id;

	@Column(name = "winner")
	int winner;

	@Column(name = "token")
	String token;

	@Column(name = "expired")
	Boolean expired;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	@OneToMany(mappedBy = "game")
	private List<Turn> turns;

	public Games(int winner) {
		this.winner = winner;
	}

	public int getId() {
		return id;
	}

	public Games() {

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
