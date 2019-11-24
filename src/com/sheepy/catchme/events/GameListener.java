package com.sheepy.catchme.events;

public interface GameListener {

	void onGameStart(GameStartEvent event);
	void onGameEnd(GameEndEvent event);
	
}
