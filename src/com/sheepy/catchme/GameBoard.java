package com.sheepy.catchme;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.entity.PlayerMP;
import com.sheepy.catchme.entitys.entity.Sheep;
import com.sheepy.catchme.entitys.entity.Werewolf;
import com.sheepy.catchme.entitys.entity.Item;
import com.sheepy.catchme.entitys.Projectile;
import com.sheepy.catchme.entitys.projectile.Ball;
import com.sheepy.catchme.enums.GameState;
import com.sheepy.catchme.events.BallHitEvent;
import com.sheepy.catchme.events.EventObserver;
import com.sheepy.catchme.events.GameEndEvent;
import com.sheepy.catchme.events.GameListener;
import com.sheepy.catchme.events.GameStartEvent;
import com.sheepy.catchme.events.PickupItemEvent;
import com.sheepy.catchme.events.WerewolfDoDamageEvent;
import com.sheepy.catchme.util.Colors;
import com.sheepy.catchme.util.Vector2D;
import com.sheepy.catchme.sounds.Sound;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GameBoard extends JPanel implements KeyListener, MouseListener, WindowListener, GameListener, Runnable {

	private static double timeTick;
	private static double maximumTimeTick = 90 * 1000;
	private static double ballDelay;
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;
	public static TileMap tileMap;
	public static EventObserver eventObserver;
	private final Set<Integer> pressed = new HashSet<Integer>();
	private List<PlayerMP> players;
	private List<Item> item;
	private List<Projectile> projectiles;
	private int selectedPlayer;
	private GameState state;
	private BufferedImage endingScene;
	private Sound sound;

	public GameBoard() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
		this(0);
	}

	public GameBoard(int selectedPlayer) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.players = new ArrayList<PlayerMP>();
		this.projectiles = new ArrayList<Projectile>();
		this.item = new ArrayList<Item>();
		this.state = GameState.PAUSE;
		this.selectedPlayer = selectedPlayer;
		//		this.sound = new Sound();
		GameBoard.tileMap = new TileMap(32, 32);
		GAME_WIDTH = GameBoard.tileMap.getWidth() * TileMap.getTileSize();
		GAME_HEIGHT = GameBoard.tileMap.getHeight() * TileMap.getTileSize();
		eventObserver = new EventObserver();
		//		this.sound.play();

		// Spawn Werewolf
		Werewolf _w = new Werewolf(32.0, 32.0, "Werewolf");
		int[] pos = GameBoard.tileMap.getRandomGroundPosition();
		_w.setX(pos[0]);
		_w.setY(pos[1]);
		//		this.players.add(_w);

		// Spawn Sheep
		//		for (int i = 0; i < 4; i++) {
		//			Sheep _s = new Sheep(32.0, 32.0, "Sheep " + (i + 1));
		//			pos = GameBoard.tileMap.getRandomGroundPosition();
		//			_s.setX(pos[0]);
		//			_s.setY(pos[1]);
		//			this.players.add(_s);
		//		}

		// Spawn Item
		Item _wi = new Item();
		_wi.setName(_wi.randomItem("Werewolf"));
		String path = "image/item_" + _wi.getName() + ".png";
		pos = GameBoard.tileMap.getRandomGroundPosition();
		_wi.setX(pos[0]);
		_wi.setY(pos[1]);
		_wi.setSpriteSheet(new SpriteSheet(path, 1));
		this.item.add(_wi);
		Item _si = new Item();
		_si.setName(_si.randomItem("Sheep"));
		pos = GameBoard.tileMap.getRandomGroundPosition();
		_si.setX(pos[0]);
		_si.setY(pos[1]);
		_si.setSpriteSheet(new SpriteSheet("image/item_s1.png", 1));
		this.item.add(_si);

		this.setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
		this.setBackground(Colors.lightblue);
		this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		eventObserver.addGameListeners(this);
		eventObserver.onGameStart(new GameStartEvent(this));
	}

	@Override
	public void run() {
		while (this.getGameState() == GameState.RUNNING) {
			try {
				Player p = this.getPlayer();
				if (p != null && p instanceof Player) {
					Vector2D v = p.getVector();
					double dx = 0, dy = 0;
					if (this.pressed.contains(65)) {
						if (p.getStatus().equals("Speed Boost")) {
							dx -= 7.5;
						} else {
							dx -= 5;
						}
					}
					if (this.pressed.contains(68)) {
						if (p.getStatus().equals("Speed Boost")) {
							dx += 7.5;
						} else {
							dx += 5;
						}
					}
					if (this.pressed.contains(87)) {
						if (p.getStatus().equals("Speed Boost")) {
							dy -= 7.5;
						} else {
							dy -= 5;
						}
					}
					if (this.pressed.contains(83)) {
						if (p.getStatus().equals("Speed Boost")) {
							dy += 7.5;
						} else {
							dy += 5;
						}
					}

					if (p.getStatus().equals("Transform") && p.getSpriteSheet().getMaxStep() != 19) {
						p.setSpriteSheet(new SpriteSheet("image/sheepy1_walk.png", 19));
					}

					if (!p.getStatus().equals("None")) {
						if (p.getBuffDuration() - 1 == 0) {
							p.setStatus("None");
							if (p instanceof Werewolf) {
								p.setSpriteSheet(new SpriteSheet("image/wolf1_walk.png", 17));
							}
							p.setBuffDuration(400);
							System.out.println("Effect worn out");
						} else
							p.setBuffDuration(p.getBuffDuration() - 1);
					}

					if (timeTick % (Game.TICK * 40 * 15) == 0) {
						this.item.clear();
						Item _wi = new Item();
						_wi.setName(_wi.randomItem("Werewolf"));
						String path = "image/item_" + _wi.getName() + ".png";
						int[] pos = GameBoard.tileMap.getRandomGroundPosition();
						_wi.setX(pos[0]);
						_wi.setY(pos[1]);
						_wi.setSpriteSheet(new SpriteSheet(path, 1));
						this.item.add(_wi);
						Item _si = new Item();
						_si.setName(_si.randomItem("Sheep"));
						pos = GameBoard.tileMap.getRandomGroundPosition();
						_si.setX(pos[0]);
						_si.setY(pos[1]);
						_si.setSpriteSheet(new SpriteSheet("image/item_s1.png", 1));
						this.item.add(_si);
					}

					v.setX(dx);
					v.setY(dy);
					p.move();
				}
				// Update Entity position
				for (Iterator<Projectile> iterProj = this.projectiles.iterator(); iterProj.hasNext();) {
					Projectile proj = iterProj.next();
					proj.move();
					if (proj instanceof Projectile) {
						if (!proj.isInGameboard() || proj.getVector().getLength() <= 0.4) {
							iterProj.remove();
							continue;
						}
						for (Iterator<PlayerMP> iterPlayer = this.players.iterator(); iterPlayer.hasNext();) {
							PlayerMP playerMP = iterPlayer.next();
							Player player = playerMP.getPlayer();
							if (player instanceof Sheep) {
								if (proj.checkCollision(player.getHitbox())) {
									if (proj instanceof Ball) {
										eventObserver.onBallHit(new BallHitEvent((Ball) proj, player));
										if (((Ball) proj).getOwner() instanceof Werewolf) {
											eventObserver.onWerewolfDoDamage(new WerewolfDoDamageEvent(
													(Werewolf) ((Ball) proj).getOwner(), (Ball) proj, player));
										}
									}
									iterPlayer.remove();
									iterProj.remove();
									continue;
								}
							}
						}
					}
				}

				for (Iterator<Item> iterItem = this.item.iterator(); iterItem.hasNext();) {
					Item item = iterItem.next();
					for (PlayerMP playerMP : this.players) {
						Player player = playerMP.getPlayer();
						if (item.checkCollision(player.getHitbox())) {
							if ((player instanceof Werewolf && !item.getName().equals("s1"))
									|| (player instanceof Sheep && item.getName().equals("s1"))) {
								eventObserver.onPickup(new PickupItemEvent(player, item));
								System.out.println(player + " " + item.getName());
								iterItem.remove();
								break;
							}
						}
					}
				}

				timeTick += Game.TICK;

				if (maximumTimeTick < timeTick || this.players.size() <= 1) {
					this.setGameState(GameState.END);
					String winTeam = "";
					if (this.players.size() > 1) {
						this.players.remove(0);
						winTeam = "Sheep";
					} else {
						Player player = this.getPlayer(0); // is it werewolf?
						if (player instanceof Werewolf) {
							winTeam = "Werewolf";	
						} else {
							winTeam = "Sheep";
						}
					}
					eventObserver.onGameEnd(new GameEndEvent(this, winTeam, this.players));
				} else {
					this.repaint();
					Thread.sleep(Game.TICK);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (this.getGameState() == GameState.RUNNING) {

			// Translate screen to player
			g2d.translate(-this.getPlayer().getX() + Game.WINDOW_WIDTH / 2,
					-this.getPlayer().getY() + Game.WINDOW_HEIGHT / 2);

			// Clear screen
			g.setColor(new Color(0, 0, 0)); // int r, int g, int b
			g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

			// Render Map
			GameBoard.tileMap.paint(g2d);

			// Render Entity
			for (Projectile _proj : this.projectiles) {
				_proj.paint(g2d);
			}

			for (Item _item : this.item) {
				_item.paint(g2d);
			}

			for (PlayerMP _player : this.players) {
				_player.getPlayer().paint(g2d);
			}

			g2d.translate(this.getPlayer().getX() - Game.WINDOW_WIDTH / 2,
					this.getPlayer().getY() - Game.WINDOW_HEIGHT / 2);

			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Kanit", Font.BOLD, 42));
			g2d.drawString("" + (int) (90 - Math.ceil(GameBoard.timeTick / 1000)), (int) Game.WINDOW_WIDTH / 2, 50);
		} else if (this.getGameState() == GameState.END) {
			g.drawImage(endingScene, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	public GameState getGameState() {
		return this.state;
	}

	public void setGameState(GameState state) {
		this.state = state;
	}

	public List<PlayerMP> getPlayers() {
		return this.players;
	}

	public Player getPlayer() {
		return this.getPlayer(this.selectedPlayer);
	}

	public Player getPlayer(int id) {
		return this.players.get(id).getPlayer();
	}

	// Keyboard listener
	@Override
	public void keyPressed(KeyEvent e) {
		// Dash
		// System.out.println(e.getKeyCode());
		// if (e.getKeyCode() == 16 && !pressed.contains(16)) {
		// this.players.get(this.selectedPlayer).getVector().multiply(2);
		// }

		pressed.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
		// System.out.println(e.getKeyCode() + this.pressed.toString());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/*
	 * Mouse Event
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// is left click ?
		// MouseEvent.BUTTON1 = left click
		Player player = this.getPlayer();
		if (player instanceof Werewolf) {
			if (e.getButton() == MouseEvent.BUTTON1 && (timeTick - ballDelay >= 2000 || ballDelay == 0.0
					|| (timeTick - ballDelay >= 1000 && player.getStatus().equals("Fast Reload")))) {
				double projSize = 18.0;
				double centerX = Game.WINDOW_WIDTH / 2;
				double centerY = Game.WINDOW_HEIGHT / 2;

				Vector2D vect = new Vector2D();
				System.out.println("x: " + e.getXOnScreen() + " " + e.getX());
				System.out.println("y: " + e.getYOnScreen() + " " + e.getY());
				vect.add((double) e.getX() - centerX, (double) e.getY() - centerY);
				vect.normalize();
				System.out.println(vect);

				double playerCenterX = player.getX() + player.getWidth() / 2;
				double playerCenterY = player.getY() + player.getHeight() / 2;
				Ball ball = new Ball(playerCenterX, playerCenterY, projSize, player, vect);
				this.projectiles.add(ball);
				ballDelay = timeTick;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	/*
	 * Window Event
	 */
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.setGameState(GameState.END);
		System.exit(1);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void onGameStart(GameStartEvent event) {
		if (event.getGame().equals(this)) {
			new Thread(event.getGame()).start();
		}
	}

	@Override
	public void onGameEnd(GameEndEvent event) {
		try {
			if (event.getGame().equals(this)) {
				if (event.getWinnerTeam().equals("Werewolf")) {
					endingScene = ImageIO.read(getClass().getResource("image/Wolf-win.png"));
				} else {
					endingScene = ImageIO.read(getClass().getResource("image/Sheep-win.png"));
				}
				new WinnerScene(_Client.client.getJFrame(), event.getWinnerTeam(), endingScene);
				this.sound.stop();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
