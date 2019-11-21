package com.sheepy.catchme.events;

import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.projectile.Ball;

public class BallHitEvent {
	
	private Ball ball;
	private Player target;
	
	public BallHitEvent(Ball ball, Player target) {
		this.ball = ball;
		this.target = target;
	}
	
	public Ball getBall() {
		return this.ball;
	}
	
	public Player getTarget() {
		return this.target;
	}

}
