package com.sheepy.catchme.entitys.entity;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.sheepy.catchme.GameBoard;
import com.sheepy.catchme.SpriteSheet;
import com.sheepy.catchme.entitys.Entitys;
import com.sheepy.catchme.events.PickupItemEvent;
import com.sheepy.catchme.events.PickupItemListener;
import com.sheepy.catchme.util.Vector2D;

public abstract class Player extends Entitys implements PickupItemListener {
	
	private String name;
	protected SpriteSheet sheet;
	private String status="None";
    private int buffDuration=200;
	
	public Player() {
		this(0, 0, 25.0, 25.0, "tester");
	}
	
	public Player(int x, int y) {
		this(x, y, 25.0, 25.0, "tester");
	}
	
	public Player(String name) {
		this(0, 0, 25.0, 25.0, name);
	}
	
	public Player(double width, double height) {
		this(0, 0, width, height, "tester");
	}
	
	public Player(double width, double height, String name) {
		this(0, 0, width, height, name);
	}
	
	public Player(int x, int y, double width, double height, String name) {
		super(x, y, width, height, new Vector2D(0, 0));
		this.name = name;
		GameBoard.eventObserver.addPickupListener(this);
	}
	
	@Override
	public abstract void paint(Graphics2D g);

	@Override
	public Shape getHitbox() {
		return new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
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

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getBuffDuration() {
		return buffDuration;
	}

	public void setBuffDuration(int buffDuration) {
		this.buffDuration = buffDuration;
	}

	@Override
    public void onPickupItem(PickupItemEvent event) {
    	System.out.println(event.getPlayer().getName() + " pick up " + 
    					   event.getItem().getName() + " at (" + event.getX() + ", " + event.getY() + ")");
    	event.getItem().buff(this);
    }
	
}
