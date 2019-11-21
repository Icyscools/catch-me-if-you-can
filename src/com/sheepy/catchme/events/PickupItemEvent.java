package com.sheepy.catchme.events;

import com.sheepy.catchme.entitys.entity.Item;
import com.sheepy.catchme.entitys.entity.Player;

public class PickupItemEvent {
	
	private Player player;
	private Item item;
	private int x;
	private int y;
	
	public PickupItemEvent(Player player, Item item) {
		this.player = player;
		this.item = item;
		this.x = (int)this.item.getX();
		this.y = (int)this.item.getY();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
