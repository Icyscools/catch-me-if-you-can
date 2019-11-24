package com.sheepy.catchme.events;

import com.sheepy.catchme.GameBoard;

public class GameStartEvent {

private GameBoard game;
	
	public GameStartEvent(GameBoard game) {
		this.game = game;
	}
	
	public GameBoard getGame() {
		return this.game;
	}

}
