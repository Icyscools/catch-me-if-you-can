package com.sheepy.catchme;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.sheepy.catchme.enums.GameState;

public class Game {
	
	public static int SIZE = 500;
	private static JFrame frame;
	private static GameBoard game;
	private static double timeTick;
	
	public static void main(String[] args) throws InterruptedException {
		frame = new JFrame("Game");
		game = new GameBoard();
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setTitle("Can me if you can");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while (true) {
			if (game.getGameState() == GameState.RUNNING) {
				game.update();
			}
			Thread.sleep(25);
		}
	}
	
	public void update(double deltaTime) {
		
	}
}
