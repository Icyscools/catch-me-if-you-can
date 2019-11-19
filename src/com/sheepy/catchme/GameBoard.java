package com.sheepy.catchme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.entity.Sheep;
import com.sheepy.catchme.entitys.entity.Werewolf;
import com.sheepy.catchme.entitys.entity.Item;
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
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;
	private final Set<Integer> pressed = new HashSet<Integer>();
	private final TileMap tileMap;
	private List<Player> players;
	private List<Item> item;
	private List<Projectile> projectiles;
	private GameState state;


	public GameBoard() throws InterruptedException {
		this.players = new ArrayList<Player>();
		this.projectiles = new ArrayList<Projectile>();
		this.item = new ArrayList<Item>();
		this.state = GameState.RUNNING;
		this.tileMap = new TileMap(32, 32);
		GAME_WIDTH = this.tileMap.getWidth() * TileMap.getTileSize();
		GAME_HEIGHT = this.tileMap.getHeight() * TileMap.getTileSize();
		
		Werewolf _w = new Werewolf();
		int[] pos1 = this.tileMap.getRandomGroundTile();
		_w.setX(TileMap.getTileSize() * pos1[0]);
		_w.setY(TileMap.getTileSize() * pos1[1]);
		Sheep _s = new Sheep();
		int[] pos2 = this.tileMap.getRandomGroundTile();
		_s.setX(TileMap.getTileSize() * pos2[0]);
		_s.setY(TileMap.getTileSize() * pos2[1]);
		
		this.players.add(_w);
		this.players.add(_s);
		this.item.add(new Item(500, 500));

		this.setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
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
		g2d.translate(-this.players.get(0).getX() + Game.WINDOW_WIDTH / 2, -this.players.get(0).getY() + Game.WINDOW_HEIGHT / 2);

		// Clear screen
		g.setColor(new Color(0, 0, 0)); // int r, int g, int b
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

		// Render Map
		this.tileMap.paint(g2d);

		// Render Entity
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
				for (Iterator<Player> iterPlayer = this.players.iterator(); iterPlayer.hasNext();) {
					Player player = iterPlayer.next();
					if (player instanceof Sheep) {
						if (proj.checkCollision(player.getHitbox())) {
							iterPlayer.remove();
							iterProj.remove();
							continue;
						}
					}
				}
			}
			proj.move();
			proj.paint(g2d);
		}
		
		for (Iterator<Item> iterItem = this.item.iterator(); iterItem.hasNext();) {
			Item item = iterItem.next();
			for (Player player : this.players) {
				if (item.checkCollision(player.getHitbox())) {
					iterItem.remove();
				}
			}
			item.paint(g2d);
		}
		
		for (Iterator<Player> iterPlayer = this.players.iterator(); iterPlayer.hasNext();) {
			Player player = iterPlayer.next();
			player.paint(g2d);
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
			double centerX = Game.WINDOW_WIDTH / 2;
			double centerY = Game.WINDOW_HEIGHT / 2;

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
