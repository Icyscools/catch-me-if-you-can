package com.sheepy.catchme.entitys;

import java.awt.Graphics2D;
import java.awt.Shape;

import com.sheepy.catchme.GameBoard;
import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.events.PickupItemEvent;
import com.sheepy.catchme.events.PlayerMoveEvent;
import com.sheepy.catchme.events.PlayerStandingEvent;
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
		if (this instanceof Projectile) {
			this.x += this.vect.getX();
			this.y += this.vect.getY();
		} else if (this.vect.getX() != 0 || this.vect.getY() != 0) {
			double nx = Math.max(Math.min(this.vect.getX() + this.x, GameBoard.GAME_WIDTH - this.width), 0);
			double ny = Math.max(Math.min(this.vect.getY() + this.y, GameBoard.GAME_HEIGHT - this.height), 0);

			if (GameBoard.tileMap.getTile(nx, ny) > 0 &&
					GameBoard.tileMap.getTile(nx + this.width, ny) > 0 &&
					GameBoard.tileMap.getTile(nx, ny + this.height) > 0 &&
					GameBoard.tileMap.getTile(nx + this.width, ny + this.height) > 0) {
				this.x = nx;
				this.y = ny;
				if (this instanceof Player) {
					GameBoard.eventObserver.onPlayerMove(new PlayerMoveEvent((Player) this, this.vect.clone()));
				}
			} else {
				if (this instanceof Player) {
					GameBoard.eventObserver.onPlayerStanding(new PlayerStandingEvent((Player) this));
					// this.x = nx - (nx - (int)(Math.ceil(nx / TileMap.getTileSize()) * TileMap.getTileSize()));
				}
			}
		} else {
			if (this instanceof Player) {
				GameBoard.eventObserver.onPlayerStanding(new PlayerStandingEvent((Player) this));
			}
		}
	}

	public abstract void paint(Graphics2D g);

	public abstract Shape getHitbox();

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

	public void setPosition(double x, double y) {
		this.x = x;
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

	public boolean isInGameboard() {
		return !(this.getX() < 0 || this.getX() > GameBoard.GAME_WIDTH || this.getY() < 0 || this.getY() > GameBoard.GAME_HEIGHT);
	}

	public boolean checkCollision(Shape bound) {
		return this.getHitbox().intersects(bound.getBounds2D());
	}
}
