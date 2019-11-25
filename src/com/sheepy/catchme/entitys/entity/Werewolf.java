package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sheepy.catchme.GameBoard;
import com.sheepy.catchme.events.PickupItemListener;
import com.sheepy.catchme.events.PlayerMoveListener;
import com.sheepy.catchme.events.WerewolfDoDamageEvent;
import com.sheepy.catchme.events.WerewolfDoDamageListener;

public class Werewolf extends Player implements PickupItemListener, PlayerMoveListener, WerewolfDoDamageListener {

	public Werewolf() {
		super();
	}

	public Werewolf(int x, int y) {
		super(x, y);
	}

	public Werewolf(String name) {
		super(name);
	}

	public Werewolf(double width, double height) {
		super(width, height);
	}

	public Werewolf(double width, double height, String name) {
		super(width, height, name);
	}

	public Werewolf(int x, int y, double width, double height, String name) {
		super(x, y, width, height, name);
		GameBoard.eventObserver.addWerewolfDoDamageListener(this);
	}

	@Override
	public void paint(Graphics2D g) {
		BufferedImage img = this.sheet.getSprite();
		g.drawImage(img, (int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), null);
		g.setColor(new Color(255, 128, 128)); // int r, int g, int b
		g.setFont(new Font("Kanit", Font.BOLD, 12));
		g.drawString(this.getName(), (int)this.getX() - 6, (int)this.getY() - 5);
	}

	@Override
	public void onWerewolfDoDamage(WerewolfDoDamageEvent event) {
		if (event.getSource().equals(this)) {
			System.out.println(event.getSource().getName() + " do damage by " + event.getDamageSource().toString() + " to " + event.getTarget().getName());
		}
	}
}
