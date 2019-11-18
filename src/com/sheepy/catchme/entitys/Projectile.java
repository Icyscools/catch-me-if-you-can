package com.sheepy.catchme.entitys;

import java.awt.Graphics2D;
import java.awt.Shape;

import com.sheepy.catchme.util.Vector2D;

public abstract class Projectile extends Entitys {
	
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
	}
	
	@Override
	public void move() {
		this.getVector().multiply(1.075);
		this.getVector().getNormalize();
		super.move();
	}
	
	public abstract void paint(Graphics2D g);
	public abstract Shape getHitbox();

}