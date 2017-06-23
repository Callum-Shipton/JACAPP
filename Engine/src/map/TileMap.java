package map;

import display.ImageProcessor;
import math.Vector2;

public class TileMap {

	private int tileWidth;

	private int widthInTiles;
	private int heightInTiles;

	private Vector2[][] backgroundTiles;
	private Vector2[][] walls;
	private Vector2[][] foregroundTiles;

	public TileMap(String levelLocation, int tileWidth) {
		this.tileWidth = tileWidth;

		TileGenerator generator = new TileGenerator(levelLocation);
		generator.generateTiles();
		widthInTiles = generator.getWidthInTiles();
		heightInTiles = generator.getHeightInTiles();
		backgroundTiles = generator.getBackgroundTiles();
		walls = generator.getWalls();
		foregroundTiles = generator.getForegroundTiles();
	}

	public void renderHighTiles() {
		ImageProcessor.irFore.draw(ImageProcessor.getImage("Walls").getID());
	}

	public void renderLowTiles() {
		ImageProcessor.irBack.draw(ImageProcessor.getImage("Floor").getID());
		ImageProcessor.irWall.draw(ImageProcessor.getImage("Walls").getID());
	}

	public int getHeight() {
		return heightInTiles * tileWidth;
	}

	public int getWidth() {
		return widthInTiles * tileWidth;
	}
}
