package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.sheepy.catchme.Camera;
import com.sheepy.catchme.entitys.Entitys;
import com.sheepy.catchme.util.Vector2D;

public class Player extends Entitys {
	
	private String name;
	
	public Player() {
		this(0, 0, 25.0, 25.0, "tester");
	}
	
	public Player(int x, int y) {
		this(x, y, 25.0, 25.0, "tester");
	}
	
	public Player(String name) {
		this(0, 0, 25.0, 25.0, name);
	}
	
	public Player(int x, int y, double width, double height, String name) {
		super(x, y, width, height, new Vector2D(0, 0));
		this.name = name;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(128, 255, 128)); // int r, int g, int b
		g.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
	}

	@Override
	public Shape getHitbox() {
		return new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
