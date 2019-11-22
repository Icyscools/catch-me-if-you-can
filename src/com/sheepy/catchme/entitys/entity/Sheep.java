package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sheepy.catchme.events.PickupItemListener;
import com.sheepy.catchme.events.PlayerMoveListener;
import com.sheepy.catchme.events.PlayerStandingEvent;

public class Sheep extends Player implements PickupItemListener, PlayerMoveListener {
	
	public Sheep() {
		super();
	}
	
	public Sheep(int x, int y) {
		super(x, y);
	}
	
	public Sheep(String name) {
		super(name);
	}

	public Sheep(double width, double height) {
		super(width, height);
	}
	
	public Sheep(double width, double height, String name) {
		super(width, height, name);
	}
	
	public Sheep(int x, int y, double width, double height, String name) {
		super(x, y, width, height, name);
	}
	
	@Override
	public void paint(Graphics2D g) {
		BufferedImage img = this.sheet.getSprite();
		g.drawImage(img, (int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), null);
		g.setColor(new Color(128, 255, 128)); // int r, int g, int b
		g.drawString(this.getName(), (int)this.getX() - 1, (int)this.getY() - 5);
	}
}
