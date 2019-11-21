package com.sheepy.catchme.events;

import com.sheepy.catchme.entitys.Projectile;
import com.sheepy.catchme.entitys.entity.Player;
import com.sheepy.catchme.entitys.entity.Werewolf;

public class WerewolfDoDamageEvent {

	private Werewolf source;
	private Projectile damageSource;
	private Player target;
	
	public WerewolfDoDamageEvent(Werewolf source, Projectile damageSource, Player target) {
		this.source = source;
		this.damageSource = damageSource;
		this.target = target;
	}
	
	public Werewolf getSource() {
		return this.source;
	}
	
	public Projectile getDamageSource() {
		return this.damageSource;
	}
	
	public Player getTarget() {
		return this.target;
	}
	
}
