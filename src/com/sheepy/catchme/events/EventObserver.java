package com.sheepy.catchme.events;

import java.util.ArrayList;
import java.util.List;

public class EventObserver {
	private List<PickupItemListener> pickUpItemListeners = new ArrayList<PickupItemListener>();
	private List<BallHitListener> ballHitListener = new ArrayList<BallHitListener>();
	private List<WerewolfDoDamageListener> werewolfDoDamageListener = new ArrayList<WerewolfDoDamageListener>();

	public void addPickupListener(PickupItemListener listener) {
		pickUpItemListeners.add(listener);
	}
	
	public void addBallHitListener(BallHitListener listener) {
		ballHitListener.add(listener);
	}
	
	public void addWerewolfDoDamageListener(WerewolfDoDamageListener listener) {
		werewolfDoDamageListener.add(listener);
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
}
