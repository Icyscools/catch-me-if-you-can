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

public class GameBoard extends JPanel implements KeyListener, MouseListener {

	private static final long serialVersionUID = 7823467533123353979L;
	private GameState state;
	private static double timeTick;
	private final Set<Character> pressed = new HashSet<Character>();
	private List<Player> players;
	private List<Enemy> enemys;
	private List<Projectile> projectiles;

	public GameBoard() throws InterruptedException {
		this.players = new ArrayList<Player>();
		this.enemys = new ArrayList<Enemy>();
		this.projectiles = new ArrayList<Projectile>();
		this.state = GameState.RUNNING;
		
		this.players.add(new Player());
		this.enemys.add(new Enemy(50, 50));

		Ball ball = new Ball();
		ball.setVector(new Vector2D(1.0, 1.0));
		this.projectiles.add(ball);
		
		this.setPreferredSize(new Dimension(Game.SIZE, Game.SIZE));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setFocusable(true);
	}

	public void update() {
		Vector2D v = this.players.get(0).getVector();
		if (this.pressed.size() > 0) {
			double dx = 0, dy = 0;
			if (this.pressed.contains('a')) {
				dx -= 0.25;
			}
			if (this.pressed.contains('d')) {
				dx += 0.25;
			}
			if (this.pressed.contains('w')) {
				dy -= 0.25;
			}
			if (this.pressed.contains('s')) {
				dy += 0.25;
			}
			if (this.pressed.isEmpty()) {
				v.multiply(0.925);
			} else {
				v.add(new Vector2D(dx, dy));
			}
//			this.players.get(0).setVector(v);
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
		
		for (Player player : this.players) {
			player.paint(g2d);
		}
		
		for (Enemy enemy : this.enemys) {
			enemy.paint(g2d);
		}
		
		for (Projectile proj : this.projectiles) {
			if (proj instanceof Projectile) {
				if (proj.getX() < 0 || proj.getX() > Game.SIZE || proj.getY() < 0 || proj.getY() > Game.SIZE) {
					try {
						this.projectiles.remove(proj);
						continue;
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
			
			proj.move();
			proj.paint(g2d);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(e.getXOnScreen() + " " + e.getYOnScreen());
		Player player = this.players.get(0);
		Vector2D v = player.getVector().clone();
		v.add((double)e.getXOnScreen(), (double)e.getYOnScreen());
		Vector2D vect = v.getNormalize();

		vect.setX(e.getXOnScreen() > player.getX() ? vect.getX() * 1 : vect.getX() * -1);
		vect.setY(e.getYOnScreen() > player.getY() ? vect.getY() * 1 : vect.getY() * -1);
		
		System.out.println(vect);
		
		Ball ball = new Ball(player.getX(), player.getY(), 10.0, 10.0, player, vect);
		this.projectiles.add(ball);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
