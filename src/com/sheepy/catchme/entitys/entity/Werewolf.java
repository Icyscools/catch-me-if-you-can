package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class Werewolf extends Player {
	
	public Werewolf() {
		super();
	}
	
	public Werewolf(int x, int y) {
		super(x, y);
	}
	
	public Werewolf(String name) {
		super(name);
	}
	
	public Werewolf(int x, int y, double width, double height, String name) {
		super(x, y, width, height, name);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(255, 128, 128)); // int r, int g, int b
		g.fillRect((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
		g.drawString(this.getName(), (int)this.getX(), (int)this.getY() - 5);
	}

}
