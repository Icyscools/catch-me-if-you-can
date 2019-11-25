package com.sheepy.catchme;

import java.util.HashMap;

public class Lobby {

	private HashMap<ClientHandler, WaitingRoom> connectionPlayer;

	public Lobby() {
		this.connectionPlayer = new HashMap<ClientHandler, WaitingRoom>();
	}

	public int getAmountPlayer() {
		return this.connectionPlayer.size();
	}

	public HashMap<ClientHandler, WaitingRoom> getConnectionPlayer() {
		return this.connectionPlayer;
	}

	public void setConnectionPlayer(HashMap<ClientHandler, WaitingRoom> client) {
		this.connectionPlayer = client;
	}

	public void addConnectionPlayer(ClientHandler handler, WaitingRoom client) {
		if (this.connectionPlayer.size() < 5) {
			this.connectionPlayer.put(handler, client);
		}
	}

	public void removeConnectionPlayer(ClientHandler handler, WaitingRoom client) {
		if (this.connectionPlayer.size() > 0) {
			this.connectionPlayer.remove(handler, client);
		}
	}

}
