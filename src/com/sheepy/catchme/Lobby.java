package com.sheepy.catchme;

import java.util.HashMap;

public class Lobby {

	private HashMap<ClientHandler, Account> connectionPlayer;

	public Lobby() {
		this.connectionPlayer = new HashMap<ClientHandler, Account>();
	}

	public int getAmountPlayer() {
		return this.connectionPlayer.size();
	}

	public HashMap<ClientHandler, Account> getConnectionPlayer() {
		return this.connectionPlayer;
	}

	public void setConnectionPlayer(HashMap<ClientHandler, Account> client) {
		this.connectionPlayer = client;
	}

	public void addConnectionPlayer(ClientHandler handler, Account client) {
		if (this.connectionPlayer.size() < 5) {
			this.connectionPlayer.put(handler, client);
		}
	}

	public void removeConnectionPlayer(ClientHandler handler, Account client) {
		if (this.connectionPlayer.size() > 0) {
			this.connectionPlayer.remove(handler, client);
		}
	}

}
