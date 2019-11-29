package com.sheepy.catchme.packets;

import com.sheepy.catchme.GameClient;
import com.sheepy.catchme.GameServer;

public class Packet00Login extends Packet {

	private String username;
	
	public Packet00Login(byte[] data) {
		super(00);
		this.username = readData(data);
	}
	
	public Packet00Login(String username) {
		super(00);
		this.username = username;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(this.getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(this.getData());
	}

	@Override
	public byte[] getData() {
		return ("00" + this.username).getBytes();
	}
	
}
