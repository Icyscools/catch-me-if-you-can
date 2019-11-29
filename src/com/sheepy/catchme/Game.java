package com.sheepy.catchme;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import com.sheepy.catchme.enums.GameState;
import com.sheepy.catchme.packets.Packet00Login;

public class Game {

	public static int WINDOW_WIDTH = 640;
	public static int WINDOW_HEIGHT = 640;
	public static int TICK = 25;
	public static String TITLE = "Sheepy VS Werewolf";
	public JFrame frame;
	private GameBoard gameBoard;

	private GameClient socketClient;
	private GameServer socketServer;
	
	public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
		Game game = new Game(new JFrame());
		game.startGame();
	}
	
	public Game() {
		this(_Client.client.getJFrame());
	}
	
	public Game(JFrame frame) {
		this.frame = frame;
	}
	
	public JFrame getJFrame() {
		return this.frame;
	}
	
	public void setJFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void startGame() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.startGame(this.frame);
	}
	
	public void startGame(JFrame frame) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
		frame.getContentPane().removeAll();
		this.gameBoard = new GameBoard();
		frame.add(this.gameBoard, BorderLayout.CENTER);
		frame.pack();
		frame.setTitle(Game.TITLE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		frame.addMouseListener(this.gameBoard);
		frame.addKeyListener(this.gameBoard);
		frame.addWindowListener(this.gameBoard);
		frame.setFocusable(true);
		frame.requestFocus();
		
		socketServer = new GameServer(this.gameBoard);
		socketServer.start();
		
		socketClient = new GameClient(this.gameBoard, this.frame, "127.0.0.1");
		socketClient.start();
		
		Packet00Login loginPacket = new Packet00Login("test");
		loginPacket.writeData(socketClient);
	}
}
