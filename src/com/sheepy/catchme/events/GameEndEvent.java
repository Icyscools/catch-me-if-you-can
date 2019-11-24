package com.sheepy.catchme.events;

import java.util.List;

import com.sheepy.catchme.GameBoard;
import com.sheepy.catchme.entitys.entity.Player;

public class GameEndEvent {
	
	private GameBoard game;
	private String winTeam;
	private List<Player> winner;
	
	public GameEndEvent(GameBoard game,  String winTeam, List<Player> winner) {
		this.game = game;
		this.winTeam = winTeam;
		this.winner = winner;
	}
	
	public GameBoard getGame() {
		return this.game;
	}
	
	public String getWinnerTeam() {
		return this.winTeam;
	}
	
	public List<Player> getWinner() {
		return this.winner;
	}

}
