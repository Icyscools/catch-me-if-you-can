package com.sheepy.catchme;

import javax.swing.*;

import com.sheepy.catchme.enums.GameState;
import com.sheepy.catchme.util.Vector2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class GameBoard extends JPanel implements KeyListener {

	private static final long serialVersionUID = 7823467533123353979L;
	private GameState state;
	private static double timeTick;
	private final Set<Character> pressed = new HashSet<Character>();
	private List<Player> players;
	private Ball ball;

	public GameBoard() throws InterruptedException {
		this.players = new ArrayList<Player>();
		this.state = GameState.RUNNING;
		this.ball = new Ball();
		this.ball.setVector(new Vector2D(1.0, 1.0));
		this.players.add(new Player());
		
		this.setPreferredSize(new Dimension(Game.SIZE, Game.SIZE));
		this.addKeyListener(this);
		this.setFocusable(true);
	}

	public void update() {
		Vector2D v = this.players.get(0).getVector();
		if (this.pressed.size() > 0) {
			double dx = 0, dy = 0;
			if (this.pressed.contains('a')) {
				dx -= 1;
			}
			if (this.pressed.contains('d')) {
				dx += 1;
			}
			if (this.pressed.contains('w')) {
				dy -= 1;
			}
			if (this.pressed.contains('s')) {
				dy += 1;
			}
			v.add(new Vector2D(dx, dy));
			this.players.get(0).setVector(v);
			System.out.println(this.players.get(0).getX() + " " + this.players.get(0).getY());
		} else {
			v.multiply(0.5);
			if (v.getX() < 0.1)
				v.setX(0.0);
			if (v.getY() < 0.1)
				v.setY(0.0);
			this.players.get(0).setVector(v);
		}
		this.players.get(0).move();
		this.repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ball.paint(g2d);
		for (Player player : this.players) {
			player.paint(g2d);
		}
	}

	public GameState getGameState() {
		return this.state;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressed.add(Character.toLowerCase(e.getKeyChar()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(Character.toLowerCase(e.getKeyChar()));
	}
}
