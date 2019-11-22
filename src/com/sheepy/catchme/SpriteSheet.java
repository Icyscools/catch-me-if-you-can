package com.sheepy.catchme;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private BufferedImage sheet;
	private double spriteWidth;
	private int spriteHeight;
	private int step;
	private int maxStep;
	private boolean animationRunning = false;
	
	public SpriteSheet(String path, int frame) throws IOException {
		this.sheet = ImageIO.read(getClass().getResource(path));
		this.spriteWidth = (double)this.sheet.getWidth() / frame;
		this.spriteHeight = this.sheet.getHeight();
		this.maxStep = frame;
		System.out.println((double)this.spriteWidth / (double)this.spriteHeight);
	}
	
	public BufferedImage getSprite() {
		return this.getSprite(animationRunning ? this.step++ % this.maxStep : 0);
	}
	
	public BufferedImage getSprite(int step) {
		BufferedImage section = sheet.getSubimage((int)(step * this.spriteWidth), 0, (int)this.spriteWidth, this.spriteHeight);
//		System.out.println(section.toString());
		return section;
	}
	
	public BufferedImage getSpriteSheet() {
		return sheet;
	}
	
	public int getStep() {
		return this.step;
	}
	
	public void setStep(int step) {
		this.step = step;
	}

	public void startAnimation() {
		this.animationRunning = true;
	}
	
	public void stopAnimation() {
		this.animationRunning = false;
	}
	
}
