package com.sheepy.catchme.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventObserver {
	private List<PickupItemListener> pickUpItemListeners = new ArrayList<PickupItemListener>();
	private List<BallHitListener> ballHitListener = new ArrayList<BallHitListener>();
	private List<WerewolfDoDamageListener> werewolfDoDamageListener = new ArrayList<WerewolfDoDamageListener>();
	private List<PlayerMoveListener> playerMoveListener = new ArrayList<PlayerMoveListener>();

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
