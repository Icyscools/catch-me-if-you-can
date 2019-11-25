package com.sheepy.catchme;

import java.io.Serializable;

public class Account implements Serializable {
	
	private Client client;
	private String username;
	
	public Account(String username) {
		this(username, null);
	}
	
	public Account(String username, Client client) {
		this.client = client;
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
