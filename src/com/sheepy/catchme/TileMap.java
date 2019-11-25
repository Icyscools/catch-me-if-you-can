package com.sheepy.catchme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sheepy.catchme.util.Colors;

public class TileMap {

	private int mapTile[][];
	private static int tileSize = 48;
	private int width, height;
	private BufferedImage[] tileImg;

	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
		mapTile = new int[height][width];
		tileImg = new BufferedImage[4];
		try {
			SpriteSheet _sheet1 = new SpriteSheet("image/floor3.png", 1);
			SpriteSheet _sheet2 = new SpriteSheet("image/floor1-1.png", 1);
			SpriteSheet _sheet3 = new SpriteSheet("image/floor1-2.png", 1);
			SpriteSheet _sheet4 = new SpriteSheet("image/floor1-3.png", 1);
			this.tileImg[0] = _sheet1.getSprite();
			this.tileImg[1] = _sheet2.getSprite();
			this.tileImg[2] = _sheet3.getSprite();
			this.tileImg[3] = _sheet4.getSprite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.generateMap(0.2);
	}

	public void generateMap(double bias) {
		// Genearate Random noise map
		int[][] cloneTile = new int[this.height][this.width];
		for (int row = 1; row < height - 1; row++) {
			for (int col = 1; col < width - 1; col++) {
				//cloneTile[row][col] = ThreadLocalRandom.current().nextInt(0, 1 + 1); // (min, max + 1)
				cloneTile[row][col] = Math.random() < 0.5 + bias ? (int)Math.rint(1 + (Math.random() * (3 - 1))) : 0;
				this.mapTile[row][col] = cloneTile[row][col];
			}
		}

		// Concat the area
		for (int row = 3; row < height - 3; row++) {
			for (int col = 3; col < width - 3; col++) {
				if (cloneTile[row][col] >= 1) {
					if (cloneTile[row][col + 2] >= 1) {
						this.mapTile[row][col + 1] = (int)Math.rint(1 + (Math.random() * (3 - 1)));
					}
					if (cloneTile[row][col - 2] >= 1) {
						this.mapTile[row][col - 1] = (int)Math.rint(1 + (Math.random() * (3 - 1)));
					}
					if (cloneTile[row + 2][col] >= 1) {
						this.mapTile[row + 1][col] = (int)Math.rint(1 + (Math.random() * (3 - 1)));
					}
					if (cloneTile[row - 2][col] >= 1) {
						this.mapTile[row - 1][col] = (int)Math.rint(1 + (Math.random() * (3 - 1)));
					}
				}
			}
		}

		cloneTile = this.mapTile.clone();
		//debugMap(cloneTile);
		
		// Concat noise
		for (int row = 8; row < height - 8; row++) {
			for (int col = 8; col < width - 8; col++) {
				if (cloneTile[row][col] == 0) {
					if (cloneTile[row][col + 2] == 0) {
						this.mapTile[row][col + 1] = 0;
					}
					if (cloneTile[row][col - 2] == 0) {
						this.mapTile[row][col - 1] = 0;
					}
					if (cloneTile[row + 2][col] == 0) {
						this.mapTile[row + 1][col] = 0;
					}
					if (cloneTile[row - 2][col] == 0) {
						this.mapTile[row - 1][col] = 0;
					}
				}
			}
		}

		// Remove noise where alone
		for (int row = 1; row < height - 2; row++) {
			for (int col = 1; col < width - 2; col++) {
				if (this.mapTile[row][col] == 0) {
					if (this.mapTile[row][col + 1] >= 1 &&
							this.mapTile[row][col - 1] >= 1 &&
							this.mapTile[row + 1][col] >= 1 &&
							this.mapTile[row - 1][col] >= 1) {
						this.mapTile[row][col] = (int)Math.rint(1 + (Math.random() * (3 - 1)));
					}
				}
			}
		}

		int rowLength = this.mapTile.length;
		int colLength = this.mapTile[0].length;
		//debugMap(this.mapTile);

		int[][] mask = this.observeGo(rowLength / 2, colLength / 2, this.mapTile.clone());
		this.mapTile = mask;
	}


	private int[][] observeGo(int row, int col, int[][] map) {
		int[][] mask = new int[map.length][map[0].length];
		if (map[row][col] == 0) {
			int dx = Math.random() > 0.5 ? -1 : 1;
			int dy = Math.random() > 0.5 ? -1 : 1;
			return this.observeGo(row + dx, col + dy, map);
		}
		return this.observeGo(row, col, map, mask.clone(), false);
	}

	private int[][] observeGo(int row, int col, int[][] map, int[][] mask, boolean found) {
		if (row > map.length - 1 || col > map[0].length - 1 || row < 0 || col < 0 || mask[row][col] != 0) {
			return mask.clone();
		} else {
			int tile = map[row][col];
			if (tile > 0) {
				mask[row][col] = tile;
				mask = this.observeGo(row + 1, col, map, mask.clone(), true);
				mask = this.observeGo(row - 1, col, map, mask.clone(), true);
				mask = this.observeGo(row, col + 1, map, mask.clone(), true);
				mask = this.observeGo(row, col - 1, map, mask.clone(), true);
			}
			return mask.clone();
		}
	}

	public void debugMap(int[][] map) {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				System.out.print(map[row][col]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public int[] getRandomGroundTile() {
		int row = (int) Math.floor(1 + Math.random() * (this.width - 2));
		int col = (int) Math.floor(1 + Math.random() * (this.height - 2));
		if (this.mapTile[row][col] == 0) {
			return getRandomGroundTile();
		} else {
			return new int[] {row, col};
		}
	}
	
	public int[] getRandomGroundPosition() {
		int pos[] = getRandomGroundTile();
		int y = pos[0] * TileMap.getTileSize();
		int x = pos[1] * TileMap.getTileSize();
		return new int[] { x, y };
		
	}
	
	public int getTile(double x, double y) {
		return getTile((int) x, (int) y);
	}
	
	public int getTile(int x, int y) {
		int row = y / TileMap.tileSize;
		int col = x / TileMap.tileSize;
		return this.mapTile[row][col];
	}

	public static int getTileSize() {
		return tileSize;
	}

	public void paint(Graphics2D g) {
		for (int row = -1; row < this.height; row++) {
			for (int col = -1; col < this.width; col++) {
				if (row < 0 || col < 0) {
					g.drawImage(this.tileImg[0], col * tileSize, row * tileSize, tileSize + 1, tileSize + 1, null);
//					g.setColor(Colors.blue);
//					g.fillRect(col * tileSize, row * tileSize, tileSize + 1, tileSize + 1);
				} else {
					int tile = this.mapTile[row][col];
					switch (tile) {
					case 1:
						g.drawImage(this.tileImg[1], col * tileSize, row * tileSize, tileSize + 1, tileSize + 1, null);
//						g.setColor(Colors.grass);
//						g.fillRect(col * tileSize, row * tileSize, tileSize + 1, tileSize + 1);
						break;
					case 2:
						g.drawImage(this.tileImg[2], col * tileSize, row * tileSize, tileSize + 1, tileSize + 1, null);
						break;
					case 3:
						g.drawImage(this.tileImg[3], col * tileSize, row * tileSize, tileSize + 1, tileSize + 1, null);
						break;
					default:	
						g.drawImage(this.tileImg[0], col * tileSize, row * tileSize, tileSize + 1, tileSize + 1, null);
//						g.setColor(Colors.blue);
					}
//					g.fillRect(col * tileSize, row * tileSize, tileSize + 1, tileSize + 1);
				}
			}
		}
	}
}
