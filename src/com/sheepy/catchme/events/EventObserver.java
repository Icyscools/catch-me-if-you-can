package com.sheepy.catchme.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventObserver {
	private List<GameListener> gameListeners = new ArrayList<GameListener>();
	private List<PickupItemListener> pickUpItemListeners = new ArrayList<PickupItemListener>();
	private List<BallHitListener> ballHitListener = new ArrayList<BallHitListener>();
	private List<WerewolfDoDamageListener> werewolfDoDamageListener = new ArrayList<WerewolfDoDamageListener>();
	private List<PlayerMoveListener> playerMoveListener = new ArrayList<PlayerMoveListener>();
	
	public void addGameListeners(GameListener listener) {
		gameListeners.add(listener);
	}
	
	public void addPickupListener(PickupItemListener listener) {
		pickUpItemListeners.add(listener);
	}
	
	public void addBallHitListener(BallHitListener listener) {
		ballHitListener.add(listener);
	}
	
	public void addWerewolfDoDamageListener(WerewolfDoDamageListener listener) {
		werewolfDoDamageListener.add(listener);
	}
	
	public void addPlayerMoveListener(PlayerMoveListener listener) {
		playerMoveListener.add(listener);
	}
	
	public void removeGameListeners(GameListener listener) {
		gameListeners.remove(listener);
	}
	
	public void removePickupListener(PickupItemListener listener) {
		pickUpItemListeners.remove(listener);
	}
	
	public void removeBallHitListener(BallHitListener listener) {
		ballHitListener.remove(listener);
	}
	
	public void removeWerewolfDoDamageListener(WerewolfDoDamageListener listener) {
		werewolfDoDamageListener.remove(listener);
	}
	
	public void removePlayerMoveListener(PlayerMoveListener listener) {
		playerMoveListener.remove(listener);
	}
	
	public void onGameStart(GameStartEvent event) {
		for (GameListener listener : gameListeners) {
			listener.onGameStart(event);
		}
	}
	
	public void onGameEnd(GameEndEvent event) {
		for (GameListener listener : gameListeners) {
			listener.onGameEnd(event);
		}
	}

	public void onPickup(PickupItemEvent event) {
		for (PickupItemListener listener : pickUpItemListeners) {
			listener.onPickupItem(event);
		}
	}
	
	public void onBallHit(BallHitEvent event) {
		for (BallHitListener listener : ballHitListener) {
			listener.onBallHit(event);
		}
	}
	
	public void onWerewolfDoDamage(WerewolfDoDamageEvent event) {
		for (WerewolfDoDamageListener listener : werewolfDoDamageListener) {
			listener.onWerewolfDoDamage(event);
		}
	}
	
	public void onPlayerMove(PlayerMoveEvent event) {
		for (PlayerMoveListener listener : playerMoveListener) {
			listener.onPlayerMove(event);
		}
	}
	
	public void onPlayerStanding(PlayerStandingEvent event) {
		for (PlayerMoveListener listener : playerMoveListener) {
			listener.onPlayerStanding(event);
		}
	}
}
