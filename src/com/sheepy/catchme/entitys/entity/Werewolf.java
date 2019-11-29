package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sheepy.catchme.GameBoard;
import com.sheepy.catchme.SpriteSheet;
import com.sheepy.catchme.events.PickupItemListener;
import com.sheepy.catchme.events.PlayerMoveListener;
import com.sheepy.catchme.events.WerewolfDoDamageEvent;
import com.sheepy.catchme.events.WerewolfDoDamageListener;

public class Werewolf extends Player implements PickupItemListener, PlayerMoveListener, WerewolfDoDamageListener {

	public Werewolf() {
		this(0, 0);
	}

	public Werewolf(int x, int y) {
		this(x, y, 25.0, 25.0, "Werewolf");
	}

	public Werewolf(String name) {
		this(25.0, 25.0, name);
	}

	public Werewolf(double width, double height) {
		this(width, height, "Werewolf");
	}

	public Werewolf(double width, double height, String name) {
		this(0, 0, width, height, name);
	}

	public Werewolf(int x, int y, double width, double height, String name) {
		super(x, y, width, height, name, null, 5555);
	}
	
	public Werewolf(int x, int y, double width, double height, String name, String ipAddr, int port) {
		super(x, y, width, height, name, ipAddr, port);
		try {
			this.setSpriteSheet(new SpriteSheet("image/wolf1_walk.png", 17));
			GameBoard.eventObserver.addWerewolfDoDamageListener(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics2D g) {
		BufferedImage img = this.sheet.getSprite();
		g.drawImage(img, (int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), null);
		g.setColor(new Color(255, 128, 128)); // int r, int g, int b
		g.setFont(new Font("Kanit", Font.BOLD, 12));
		g.drawString(this.getName(), (int)this.getX() - 6, (int)this.getY() - 5);
	}

	@Override
	public void onWerewolfDoDamage(WerewolfDoDamageEvent event) {
		if (event.getSource().equals(this)) {
			System.out.println(event.getSource().getName() + " do damage by " + event.getDamageSource().toString() + " to " + event.getTarget().getName());
		}
	}
}
