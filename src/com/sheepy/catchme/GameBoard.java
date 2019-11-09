package com.sheepy.catchme;

import javax.swing.*;

import com.sheepy.catchme.entitys.entity.Enemy;
import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.Projectile;
import com.sheepy.catchme.entitys.projectile.Ball;
import com.sheepy.catchme.enums.GameState;
import com.sheepy.catchme.util.Vector2D;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
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

		// Render Entity
		for (Iterator<Player> iterPlayer = this.players.iterator(); iterPlayer.hasNext();) {
			Player player = iterPlayer.next();
			player.paint(g2d);
		}

		for (Iterator<Enemy> iterEnemy = this.enemys.iterator(); iterEnemy.hasNext();) {
			Enemy enemy = iterEnemy.next();
			if (!this.projectiles.isEmpty()) {
				for (Projectile proj : this.projectiles) {
					if (enemy.checkCollision(proj.getHitbox())) {
						iterEnemy.remove();
					}
				}
			}
			enemy.paint(g2d);
		}

		for (Iterator<Projectile> iterProj = this.projectiles.iterator(); iterProj.hasNext();) {
			Projectile proj = iterProj.next();
			if (proj instanceof Projectile) {
				if (!proj.isVisible()) {
					try {
						iterProj.remove();
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
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Player player = this.players.get(0);
		double projSize = 8.0;
		double centerX = player.getX() + player.getWidth() / 2;
		double centerY = player.getY() + player.getHeight() / 2;

		Vector2D vect = new Vector2D();
		vect.add((double) e.getX() - centerX, (double) e.getY() - centerY);
		System.out.println(vect);
		vect = vect.getNormalize();

		Ball ball = new Ball(centerX, centerY, projSize, projSize, player, vect);
		this.projectiles.add(ball);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
