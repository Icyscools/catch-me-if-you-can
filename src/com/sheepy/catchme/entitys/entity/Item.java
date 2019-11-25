package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.sheepy.catchme.SpriteSheet;
import com.sheepy.catchme.entitys.Entitys;
import com.sheepy.catchme.util.Vector2D;

public class Item extends Entitys {

	private int respawnTime;
	private String name;
	protected SpriteSheet sheet;

	public Item() {
		this(0, 0, 20.0, 20.0, 10, "Tester Item");
	}

	public Item(int x, int y) {
		this(x, y, 20.0, 20.0, 10, "Tester Item");
	}

	public Item(String name) {
		this(0, 0, 20.0, 20.0, 10, name);
	}

	public Item(int x, int y, double width, double height, int respawnTime, String name) {
		super(x, y, width, height, new Vector2D(0, 0));
		this.respawnTime = respawnTime;
		this.name = name;
	}

	public int getRespawnTime() {
		return respawnTime;
	}

	public void setRespawnTime(int respawnTime) {
		this.respawnTime = respawnTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SpriteSheet getSpriteSheet() {
		return this.sheet;
	}
	
	public void setSpriteSheet(SpriteSheet sheet) {
		this.sheet = sheet;
	}


	public void paint(Graphics2D g) {
		BufferedImage img = this.sheet.getSprite();
		g.drawImage(img, (int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight(), null);
		g.setColor(new Color(255, 127, 80)); // int r, int g, int b
	}

	public Shape getHitbox() {
		return new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public void buff(Player p) {
		switch (this.name) {
		case "w1":
			p.setStatus("Speed Boost");
			System.out.println("Speed Boost");
			break;
		case "w2":
			p.setStatus("Transform");
			System.out.println("Transform");
			break;
		case "w3":
			p.setStatus("Fast Reload");
			System.out.println("Fast Reload");
			break;
		default:
			p.setStatus("Speed Boost");
			System.out.println("Speed Boost");
			break;
		}
	}
	
	public String randomItem(String type) {
		int random = type.equals("Werewolf") ? (int) (Math.random() * 3) : 3;
		switch (random) {
		case 0:
			return "w1";
		case 1:
			return "w2";
		case 2:
			return "w3";
		default:
			return "s1";
		}
	}

}
