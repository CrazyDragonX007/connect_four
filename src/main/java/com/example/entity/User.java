package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}

	@Column(name="username")
	@Id
	private String username;
	
	@Column(name="password")
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
	
}
