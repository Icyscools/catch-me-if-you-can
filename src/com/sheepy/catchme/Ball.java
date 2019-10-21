package com.sheepy.catchme;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sheepy.catchme.util.Vector2D;

public class Ball extends Projectile {
	
	private Player owner;
	
	public Ball() {
		super(0.0, 0.0);
		this.owner = null;
	}
	
	public Ball(Player player) {
		this(player.getX(), player.getY(), 10.0, 10.0, player, player.getVector());
		this.owner = player;
	}
	
	public Ball(Player player, Vector2D v) {
		this(player.getX(), player.getY(), 10.0, 10.0, player, v);
		this.owner = player;
	}
	
	public Ball(double x, double y, double width, double height, Player player, Vector2D vector) {
		super(x, y, width, height, vector);
		this.owner = player;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(30, 30, 30));
		g.fillOval((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight());
	}
	
	public Player getOwner() {
		return this.owner;
	}

}
