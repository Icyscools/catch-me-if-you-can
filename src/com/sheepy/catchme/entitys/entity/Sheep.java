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

public class Sheep extends Player implements PickupItemListener, PlayerMoveListener {
	
	public Sheep() {
		this(0, 0);
	}

	public Sheep(int x, int y) {
		this(x, y, 25.0, 25.0, "Sheep");
	}

	public Sheep(String name) {
		this(25.0, 25.0, name);
	}

	public Sheep(double width, double height) {
		this(width, height, "Sheep");
	}

	public Sheep(double width, double height, String name) {
		this(0, 0, width, height, name);
	}

	public Sheep(int x, int y, double width, double height, String name) {
		super(x, y, width, height, name, null, 5555);
	}
	
	public Sheep(int x, int y, double width, double height, String name, String ipAddr, int port) {
		super(x, y, width, height, name, ipAddr, port);
		try {
			this.setSpriteSheet(new SpriteSheet("image/sheepy1_walk.png", 19));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics2D g) {
		BufferedImage img = this.sheet.getSprite();
		g.drawImage(img, (int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), null);
		g.setColor(new Color(128, 255, 128)); // int r, int g, int b
		g.setFont(new Font("Kanit", Font.BOLD, 12));
		g.drawString(this.getName(), (int)this.getX() - 6, (int)this.getY() - 5);
	}
}
