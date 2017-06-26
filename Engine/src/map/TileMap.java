package map;

import display.ImageProcessor;
import math.Vector2;

public class TileMap {

	private int tileWidth;

	private int width;
	private int height;

	private Vector2[][] backgroundTiles;
	private Vector2[][] walls;
	private Vector2[][] foregroundTiles;

	TileGenerator generator;

	public TileMap(String levelLocation, int tileWidth) {
		this.tileWidth = tileWidth;

		generator = new TileGenerator(levelLocation);

		width = generator.getWidth();
		height = generator.getHeight();

		backgroundTiles = generator.getBackgroundTiles();
		walls = generator.getWalls();
	}

	public void renderHighTiles() {
		ImageProcessor.irFore.draw(ImageProcessor.getImage("Walls").getID());
	}

	public void renderLowTiles() {
		ImageProcessor.irBack.draw(ImageProcessor.getImage("Floor").getID());
		ImageProcessor.irWall.draw(ImageProcessor.getImage("Walls").getID());
	}

	public int getHeight() {
		return height * tileWidth;
	}

	public int getWidth() {
		return width * tileWidth;
	}
}
