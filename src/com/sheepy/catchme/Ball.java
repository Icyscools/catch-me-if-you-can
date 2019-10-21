package com.sheepy.catchme;

import java.awt.Graphics2D;

public class Ball extends Projectile {
	
	private Player owner;
	
	public Ball() {
		this(null);
	}
	
	public Ball(Player player) {
		super(0.0, 0.0);
		this.owner = player;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.fillOval((int)this.getX(), (int)this.getY(), 25, 25);
	}
	
	public Player getOwner() {
		return this.owner;
	}

}
