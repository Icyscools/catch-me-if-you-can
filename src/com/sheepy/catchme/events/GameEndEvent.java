package com.sheepy.catchme.events;

import com.sheepy.catchme.GameBoard;
import com.sheepy.catchme.entitys.entity.Player;

public class GameEndEvent {
	
	private GameBoard game;
	private Player[] winner;
	
	public GameEndEvent(GameBoard game, Player[] winner) {
		this.game = game;
		this.winner = winner;
	}
	
	public GameBoard getGame() {
		return this.game;
	}

}
