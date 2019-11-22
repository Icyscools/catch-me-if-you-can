package com.sheepy.catchme.events;

public interface PlayerMoveListener {
	
	void onPlayerMove(PlayerMoveEvent event);
	void onPlayerStanding(PlayerStandingEvent event);

}
