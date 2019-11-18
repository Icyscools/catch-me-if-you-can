package com.sheepy.catchme.entitys.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.sheepy.catchme.entitys.Entitys;
import com.sheepy.catchme.util.Vector2D;

public class Item extends Entitys {

    private int respawnTime;
    private String name;

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

    public void paint(Graphics2D g) {
        g.setColor(new Color(255,127,80)); // int r, int g, int b
        g.fillRect((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
    }

    public Shape getHitbox() {
        return new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
