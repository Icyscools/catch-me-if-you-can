package com.sheepy.catchme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sheepy.catchme.entitys.entity.Enemy;
import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.Projectile;
import com.sheepy.catchme.entitys.projectile.Ball;
import com.sheepy.catchme.enums.GameState;
import com.sheepy.catchme.util.Colors;
import com.sheepy.catchme.util.Vector2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.*;

public class GameBoard extends JPanel implements KeyListener, MouseListener {

	private static GameBoard game;
	private static JFrame frame;
	private static double timeTick;
	public static int WINDOW_WIDTH = 1366;
	public static int WINDOW_HEIGHT = 768;
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;
	private final Set<Integer> pressed = new HashSet<Integer>();
	private final TileMap tileMap;
	private List<Player> players;
	private List<Enemy> enemys;
	private List<Projectile> projectiles;
	private GameState state;


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
				timeTick++;
				Thread.sleep(25);
			}
		}
	}

	public GameBoard() throws InterruptedException {
		this.players = new ArrayList<Player>();
		this.enemys = new ArrayList<Enemy>();
		this.projectiles = new ArrayList<Projectile>();
		this.state = GameState.RUNNING;
		this.tileMap = new TileMap(32, 32);
		GAME_WIDTH = this.tileMap.getWidth() * TileMap.getTileSize();
		GAME_HEIGHT = this.tileMap.getHeight() * TileMap.getTileSize();

		this.players.add(new Player());
		Player p = this.players.get(0);
		int[] pos = this.tileMap.getRandomGroundTile();
		p.setX(TileMap.getTileSize() * pos[0]);
		p.setY(TileMap.getTileSize() * pos[1]);
		this.enemys.add(new Enemy(50, 50));

		this.setPreferredSize(new Dimension(GameBoard.WINDOW_WIDTH, GameBoard.WINDOW_HEIGHT));
		this.setBackground(Colors.blue);
		this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setFocusable(true);
	}

	public void update() {
		Player p = this.players.get(0);
		Vector2D v = p.getVector();
		if (this.pressed.size() > 0) {
			double dx = 0, dy = 0;
			if (this.pressed.contains(65)) {
				dx -= 1;
			}
			if (this.pressed.contains(68)) {
				dx += 1;
			}
			if (this.pressed.contains(87)) {
				dy -= 1;
			}
			if (this.pressed.contains(83)) {
				dy += 1;
			}
			if (!this.pressed.isEmpty()) {
				// this.players.get(0).setX(this.players.get(0).getX() + dx);
				// this.players.get(0).setY(this.players.get(0).getY() + dy);
				v.add(dx, dy);
			} else if (this.pressed.size() <= 1 && this.pressed.contains(16)){
				if (v.getMagnitude() < 0.5) {
					v.setX(0);
					v.setY(0);
				} else {
					v.multiply(0.3);
				}
			}
			// this.players.get(0).setVector(v);
			// System.out.println(this.players.get(0).getX() + " " + this.players.get(0).getY());
		} else {
			v.multiply(0.5);
			if (v.getX() < 0.1) v.setX(0.0);
			if (v.getY() < 0.1) v.setY(0.0);
			this.players.get(0).setVector(v);
		}
		this.players.get(0).move();
		this.repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Translate to player
		g2d.translate(-this.players.get(0).getX() + WINDOW_WIDTH / 2, -this.players.get(0).getY() + WINDOW_HEIGHT / 2);

		// Clear screen
		g.setColor(new Color(0, 0, 0)); // int r, int g, int b
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

		// Render Map
		this.tileMap.paint(g2d);

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
				if (!proj.isInGameboard()) {
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

	// Keyboard listener
	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 16 && !pressed.contains(16)) {
			this.players.get(0).getVector().multiply(2);
		}
		pressed.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	// Mouse listener
	@Override
	public void mousePressed(MouseEvent e) {
		// is left click ?
		// MouseEvent.BUTTON1 = left click
		if (e.getButton() == MouseEvent.BUTTON1) {
			Player player = this.players.get(0);
			double projSize = 20.0;
			double centerX = WINDOW_WIDTH / 2;
			double centerY = WINDOW_HEIGHT / 2;

			Vector2D vect = new Vector2D();
			System.out.println("x: " + e.getXOnScreen() + " " + e.getX());
			System.out.println("y: " + e.getYOnScreen() + " " + e.getY());
			vect.add((double) e.getX() - centerX, (double) e.getY() - centerY);
			vect = vect.getNormalize();
			System.out.println(vect);

			double playerCenterX = player.getX() + player.getWidth() / 2;
			double playerCenterY = player.getY() + player.getHeight() / 2;
			Ball ball = new Ball(playerCenterX, playerCenterY, projSize, player, vect);
			this.projectiles.add(ball);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
