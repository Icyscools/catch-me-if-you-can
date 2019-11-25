package com.sheepy.catchme.entitys.projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.events.BallHitEvent;
import com.sheepy.catchme.events.BallHitListener;
import com.sheepy.catchme.GameBoard;
import com.sheepy.catchme.entitys.Projectile;
import com.sheepy.catchme.util.Vector2D;

public class Ball extends Projectile implements BallHitListener {

	private Player owner;
	private Ellipse2D hitbox;

	public Ball() {
		super(0.0, 0.0);
		this.owner = null;
	}

	public Ball(Player player) {
		this(player.getX(), player.getY(), 10.0, 10.0, player, player.getVector());
	}

	public Ball(Player player, Vector2D v) {
		this(player.getX(), player.getY(), 10.0, 10.0, player, v);
	}
	
	public Ball(double size, Player player, Vector2D v) {
		this(player.getX(), player.getY(), size, size, player, v);
	}
	
	public Ball(double x, double y, double size, Player player, Vector2D v) {
		this(x, y, size, size, player, v);
	}

	public Ball(double x, double y, double width, double height, Player player, Vector2D vector) {
		super(x, y, width, height, vector);
		this.owner = player;
		this.hitbox = new Ellipse2D.Double(x, y, width, height);
		GameBoard.eventObserver.addBallHitListener(this);
	}

	@Override
	public void paint(Graphics2D g) {
		this.hitbox.setFrame(this.getX() - this.getWidth() / 2, this.getY() - this.getHeight() / 2, this.getWidth(),
				this.getHeight());
		g.setColor(new Color(187, 128, 132));
		g.fill(this.hitbox);
	}

	public Player getOwner() {
		return this.owner;
	}

	@Override
	public Shape getHitbox() {
		return this.hitbox;
	}

	@Override
	public void onBallHit(BallHitEvent event) {
		if (event.getBall().equals(this)) {
			System.out.println(event.toString());
		}
	}

}
