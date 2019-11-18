package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class Sheep extends Player {
	
	public Sheep() {
		super();
	}
	
	public Sheep(int x, int y) {
		super(x, y);
	}
	
	public Sheep(String name) {
		super(name);
	}
	
	public Sheep(int x, int y, double width, double height, String name) {
		super(x, y, width, height, name);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(128, 255, 128)); // int r, int g, int b
		g.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
		g.drawString(this.getName(), (int)this.getX(), (int)this.getY() - 5);
	}

}
