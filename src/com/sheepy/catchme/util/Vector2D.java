package com.sheepy.catchme.util;

public class Vector2D {
	
	private double x;
	private double y;
	
	public Vector2D() {
		this(0.0, 0.0);
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D add(Vector2D vector) {
		this.x += vector.getX();
		this.y += vector.getY();
		return this;
	}
	
	public Vector2D multiply(double m) {
		this.x *= m;
		this.y *= m;
		return this;
	}
	
	public Vector2D multiply(Vector2D vector) {
		return this;
	}
	
	public Vector2D getNormalize() {
		double magnitude = this.getMagnitude();
		this.x = this.x / magnitude;
		this.y = this.y / magnitude;
		return this;
	}
	
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	

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
	
	@Override
	public String toString() {
		return this.x + ", " + this.y; 
	}

}
