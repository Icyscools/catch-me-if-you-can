package com.sheepy.catchme.events;

import com.sheepy.catchme.entitys.entity.Player;

public class PlayerStandingEvent {
	
	private Player player;
	
	public PlayerStandingEvent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
