package com.sheepy.catchme;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.entity.PlayerMP;
import com.sheepy.catchme.entitys.entity.Sheep;
import com.sheepy.catchme.enums.GameState;
import com.sheepy.catchme.packets.Packet;
import com.sheepy.catchme.packets.Packet.PacketTypes;
import com.sheepy.catchme.packets.Packet00Login;

public class GameServer extends Thread {

	private DatagramSocket socket;
	private GameBoard game;
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

	public GameServer() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
		this(new GameBoard());
	}

	public GameServer(GameBoard game) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(5555);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				this.socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//			String message = new String(packet.getData(), Charset.forName("UTF-8")).trim();
//			System.out.println("Client [" + packet.getAddress().getHostAddress() + ":" + 
//					packet.getPort() + "] >> " + message);
//			if (message.equalsIgnoreCase("ping")) {
//				Object[] client = new Object[2];
//				client[0] = packet.getAddress();
//				client[1] = packet.getPort();
//				PlayerMP player = new PlayerMP(new Sheep(300, 300), packet.getAddress(), packet.getPort());
//				this.connectedPlayers.add(player);
//				this.game.getPlayers().add();
//				this.game.setGameState(GameState.RUNNING);
//				sendDataToAllClients((packet.getAddress().getHostAddress() + ":" + 
//						packet.getPort() + " pong").getBytes());
//				sendDataToAllClients(("02").getBytes());
//			}
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data);
		PacketTypes type = Packet.lookupPacket(message);
		switch (type) {
		case INVAILD:
			break;
		case LOGIN:
			Packet00Login packet = new Packet00Login(data);
			System.out.println("Login [" + address.getHostAddress() + ":" + 
					port + "] >> " + message);
			PlayerMP player =  new PlayerMP(new Sheep(100, 100, 25.0, 25.0, "sheep"), address, port);
			this.connectedPlayers.add(player);
			this.game.getPlayers().add(player);
			break;
		case DISCONNECT:
			break;
		default:
			System.out.println("Client [" + address.getHostAddress() + ":" + 
			port + "] >> " + message);
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			this.socket.send(packet);
			System.out.println("Sending pong to " + packet.getAddress().getHostAddress() + ":" +
					packet.getPort() + "!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		if (this.connectedPlayers.size() > 0) {
			for (PlayerMP player : this.connectedPlayers) {
				sendData(data, player.getAddress(), player.getPort());
			}
		}
	}

	public static void main(String[] args) {
		try {
			new GameServer().start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
