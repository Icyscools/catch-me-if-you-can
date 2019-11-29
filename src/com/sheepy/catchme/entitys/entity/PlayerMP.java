package com.sheepy.catchme.entitys.entity;

import java.net.InetAddress;

public class PlayerMP {
	
	private Player player;
	private InetAddress ipAddress;
	private int port;
	
	public PlayerMP(Player player, InetAddress ipAddress, int port) {
		this.player = player;
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public Player getPlayer() { 
		return this.player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public InetAddress getAddress() {
		return this.getAddress();
	}
	
	public void setAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

}
