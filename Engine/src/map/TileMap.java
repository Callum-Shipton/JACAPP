package map;

import org.joml.Vector2f;

public class TileMap {

	private int width;
	private int height;

	private Vector2f[][] backgroundTiles;
	private Vector2f[][] walls;

	TileGenerator generator;

	public TileMap(String levelLocation) {

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

	public Vector2f[][] getWalls() {
		return walls;
	}

	public Vector2f[][] getBackgroundTiles() {
		return backgroundTiles;
	}
}
