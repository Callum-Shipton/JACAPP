package map;

import math.Vector2;

public class TileMap {

	private int tileWidth;

	private int width;
	private int height;

	private Vector2[][] backgroundTiles;
	private Vector2[][] walls;

	TileGenerator generator;

	public TileMap(String levelLocation, int tileWidth) {
		this.tileWidth = tileWidth;

		generator = new TileGenerator(levelLocation);

		width = generator.getWidth();
		height = generator.getHeight();
	}

	public void init() {
		generator.generateTiles();
		backgroundTiles = generator.getBackgroundTiles();
		walls = generator.getWalls();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Vector2[][] getWalls() {
		return walls;
	}

	public Vector2[][] getBackgroundTiles() {
		return backgroundTiles;
	}
}
