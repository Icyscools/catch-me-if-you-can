package com.sheepy.catchme.entitys;

import java.awt.Graphics2D;
import java.awt.Shape;

import com.sheepy.catchme.util.Vector2D;

public abstract class Projectile extends Entitys {
	
	private double velocity;
	
	public Projectile() {
		this(0.0, 0.0, 5.0, 5.0, new Vector2D());
	}
	
	public Projectile(double x, double y) {
		this(x, y, 5.0, 5.0, new Vector2D());
	}
	
	public Projectile(double x, double y, double width, double height) {
		this(x, y, width, height, new Vector2D());
	}
	
	public Projectile(double x, double y, double width, double height, Vector2D vect) {
		super(x, y, width, height, vect);
		this.velocity = 1.5;
	}
	
	@Override
	public void move() {
		velocity -= 0.03225;
		this.getVector().multiply(velocity);
		super.move();
	}
	
	public abstract void paint(Graphics2D g);
	public abstract Shape getHitbox();

}
