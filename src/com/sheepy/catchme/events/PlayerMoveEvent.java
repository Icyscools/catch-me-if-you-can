package com.sheepy.catchme.events;

import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.util.Vector2D;

public class PlayerMoveEvent {
	
	private Player player;
	private Vector2D direction;
	
	public PlayerMoveEvent(Player player, Vector2D direction) {
		this.player = player;
		this.direction = direction;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Vector2D getDirection() {
		return this.direction;
	}

}
