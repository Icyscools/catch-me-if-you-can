package com.sheepy.catchme;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.swing.JFrame;

import com.sheepy.catchme.enums.GameState;

public class Game {

	public static int WINDOW_WIDTH = 640;
	public static int WINDOW_HEIGHT = 640;
	public static int TICK = 25;
	public static String TITLE = "Catch me if you can";
	public static JFrame frame;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		Game.frame = new JFrame("Game");
		startGame(Game.frame);
	}
	
	public static void startGame(JFrame frame) throws InterruptedException, IOException {
		frame.getContentPane().removeAll();
		GameBoard game = new GameBoard();
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setTitle(Game.TITLE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		frame.addMouseListener(game);
		frame.addKeyListener(game);
		frame.addWindowListener(game);
		frame.setFocusable(true);
		frame.requestFocus();
		new Thread(game).start();
	}
}
