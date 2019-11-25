package com.sheepy.catchme;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.sheepy.catchme.enums.GameState;

public class Game {

	public static int WINDOW_WIDTH = 640;
	public static int WINDOW_HEIGHT = 640;
	public static int TICK = 25;
	public static String TITLE = "Sheepy VS Werewolf";
	public JFrame frame;
	private GameBoard gameBoard;
	private String roomID;
	private List<Client> clientConnected;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		Game game = new Game();
		game.startGame();
	}
	
	public Game() {
		this(new JFrame(Game.TITLE), "5050", new ArrayList<Client>());
	}
	
	public Game(JFrame frame, String roomID) {
		this(frame, roomID, new ArrayList<Client>());
	}

	public Game(JFrame frame, String roomID, List<Client> clients) {
		this.frame = frame;
		this.roomID = roomID;
		this.clientConnected = clients;
	}
	
	public JFrame getJFrame() {
		return this.frame;
	}
	
	public void setJFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public String getRoomID() {
		return this.roomID;
	}
	
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	
	public void startGame() throws InterruptedException, IOException {
		this.startGame(this.getJFrame());
	}
	
	public void startGame(JFrame frame) throws InterruptedException, IOException {
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
	}

    static void WaitingRoom(JFrame fr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void StartScene(JFrame fr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
