package com.sheepy.catchme;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.entity.Sheep;
import com.sheepy.catchme.entitys.entity.Werewolf;
import com.sheepy.catchme.entitys.entity.Item;
import com.sheepy.catchme.entitys.Projectile;
import com.sheepy.catchme.entitys.projectile.Ball;
import com.sheepy.catchme.enums.GameState;
import com.sheepy.catchme.util.Colors;
import com.sheepy.catchme.util.Vector2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GameBoard extends JPanel implements KeyListener, MouseListener, Runnable {

    private static double timeTick;
    private static double ballDelay;
    public static int GAME_WIDTH;
    public static int GAME_HEIGHT;
    public static TileMap tileMap;
    private final Set<Integer> pressed = new HashSet<Integer>();
    private List<Player> players;
    private List<Item> item;
    private List<Projectile> projectiles;
    private GameState state;

    public GameBoard() throws InterruptedException, IOException {
        this.players = new ArrayList<Player>();
        this.projectiles = new ArrayList<Projectile>();
        this.item = new ArrayList<Item>();
        this.state = GameState.RUNNING;
        GameBoard.tileMap = new TileMap(64, 64);
        GAME_WIDTH = GameBoard.tileMap.getWidth() * TileMap.getTileSize();
        GAME_HEIGHT = GameBoard.tileMap.getHeight() * TileMap.getTileSize();

        Werewolf _w = new Werewolf(32.0, 32.0);
        int[] pos1 = GameBoard.tileMap.getRandomGroundPosition();
        _w.setX(pos1[0]);
        _w.setY(pos1[1]);
        _w.setSpriteSheet(new SpriteSheet("image/wolf1_walk.png", 17));

        Sheep _s = new Sheep(32.0, 32.0);
        int[] pos2 = GameBoard.tileMap.getRandomGroundPosition();
        _s.setX(pos1[0]);
        _s.setY(pos1[1]);
        _s.setSpriteSheet(new SpriteSheet("image/sheepy1_walk.png", 19));

        this.players.add(_w);
        this.players.add(_s);
        this.item.add(new Item(500, 500));

        this.setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
        this.setBackground(Colors.blue);
        this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        // this.addKeyListener(this);
        // this.addMouseListener(this);
        // this.setFocusable(true);
        // this.requestFocus();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Player p = this.players.get(0);
                Vector2D v = p.getVector();
                double dx = 0, dy = 0;
                if (this.pressed.contains(65)) {
                    if (p.getStatus().equals("Fast")) {
                        dx -= 7.5;
                    } else {
                        dx -= 5;
                    }

                }
                if (this.pressed.contains(68)) {
                    if (p.getStatus().equals("Fast")) {
                        dx += 7.5;
                    } else {
                        dx += 5;
                    }
                }
                if (this.pressed.contains(87)) {
                    if (p.getStatus().equals("Fast")) {
                        dy -= 7.5;
                    } else {
                        dy -= 5;
                    }
                }
                if (this.pressed.contains(83)) {
                    if (p.getStatus().equals("Fast")) {
                        dy += 7.5;
                    } else {
                        dy += 5;
                    }
                }

                v.setX(dx);
                v.setY(dy);
                timeTick += Game.TICK;

                this.players.get(0).move();
                this.repaint();
                Thread.sleep(Game.TICK);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Translate screen to player
        g2d.translate(-this.players.get(0).getX() + Game.WINDOW_WIDTH / 2, -this.players.get(0).getY() + Game.WINDOW_HEIGHT / 2);

        // Clear screen
        g.setColor(new Color(0, 0, 0)); // int r, int g, int b
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Render Map
        GameBoard.tileMap.paint(g2d);

        // Render Entity
        for (Iterator<Projectile> iterProj = this.projectiles.iterator(); iterProj.hasNext();) {
            Projectile proj = iterProj.next();
            if (proj instanceof Projectile) {
                if (!proj.isInGameboard()) {
                    iterProj.remove();
                    continue;
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
                    item.buff(player);
                    iterItem.remove();
                }
            }
            item.paint(g2d);
        }

        for (Iterator<Player> iterPlayer = this.players.iterator(); iterPlayer.hasNext();) {
            Player player = iterPlayer.next();
//			if (player instanceof Sheep) {
//				try {
//					BufferedImage img = sheet.getSubimage(0, 0, 191, 191);
//					System.out.println(img.toString());
//					g2d.drawImage(img, (int)player.getX(), (int)player.getY(), 36, 36, null);
//					g2d.setColor(new Color(128, 255, 128)); // int r, int g, int b
////					g.drawImage(img, 0, 0, (int)this.getWidth(), (int)this.getHeight(), null);
////					g.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
//					g2d.drawString(player.getName(), (int)player.getX(), (int)player.getY() - 5);
//					continue;
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
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
        //		if (e.getKeyCode() == 16 && !pressed.contains(16)) {
        //			this.players.get(0).getVector().multiply(2);
        //		}

        pressed.add(e.getKeyCode());
        // System.out.println(e.getKeyCode() + this.pressed.toString());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
        // System.out.println(e.getKeyCode() + this.pressed.toString());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Mouse listener
    @Override
    public void mousePressed(MouseEvent e) {
        // is left click ?
        // MouseEvent.BUTTON1 = left click
        if (e.getButton() == MouseEvent.BUTTON1 && (timeTick - ballDelay >= 2000 || ballDelay == 0.0)) {
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
            ballDelay = timeTick;
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

}
