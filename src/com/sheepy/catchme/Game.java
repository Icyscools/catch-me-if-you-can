package com.sheepy.catchme;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.sheepy.catchme.enums.GameState;

public class Game {
	
	public static int WINDOW_WIDTH = 640;
	public static int WINDOW_HEIGHT = 640;
	public static JFrame frame;
	
	public static void main(String[] args) throws InterruptedException {
		frame = new JFrame("Game");
		GameBoard game = new GameBoard();
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setTitle("Catch me if you can");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		while (true) {
			if (game.getGameState() == GameState.RUNNING) {
				game.update();
				Thread.sleep(25);
			}
		}
	}
}
