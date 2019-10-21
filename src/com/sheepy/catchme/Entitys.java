package com.sheepy.catchme;

import java.awt.Graphics2D;

import com.sheepy.catchme.util.Vector2D;

public abstract class Entitys {
	private double x;
	private double y;
	private double width;
	private double height;
	private Vector2D vect;

	public Entitys() {
		this(0.0, 0.0, 1.0, 1.0, new Vector2D());
	}

	public Entitys(double x, double y) {
		this(x, y, 1.0, 1.0, new Vector2D());
	}

	public Entitys(double x, double y, Vector2D vect) {
		this(x, y, 1.0, 1.0, vect);
	}
	
	public Entitys(double x, double y, double width, double height, Vector2D vect) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.vect = vect;
	}

	public void move() {
		this.x = Math.max(Math.min(this.vect.getX() + this.x, Game.SIZE - this.width), 0);
		this.y = Math.max(Math.min(this.vect.getY() + this.y, Game.SIZE - this.height), 0);
	}

	public abstract void paint(Graphics2D g);

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}

	public Vector2D getVector() {
		return vect;
	}

	public void setVector(Vector2D vect) {
		this.vect = vect;
	}
}
