package map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import logging.Logger;
import math.Vector2;

public class TileGenerator {

	private BufferedImage mapImage;

	private Vector2[][] backgroundTiles;
	private Vector2[][] walls;
	private int[][] backgroundTileTypes;
	private int[][] wallTypes;

	// RGB integer values for tiles
	private static final int BROWNWALL_COLOR = -7864299;
	private static final int GREYWALL_COLOR = -8421505;
	private static final int LIGHTWATER_COLOR = -16735512;
	private static final int DARKWATER_COLOR = -12629812;

	private static final int GRASS_COLOR = -4856291;
	private static final int PATH_COLOR = -1055568;

	private static final int GRASS = 1;
	private static final int PATH = 2;

	private static final int WALL = 1;
	private static final int WATER = 2;

	private int width;
	private int height;

	public TileGenerator(String fileLocation) {
		loadMap(fileLocation);
		width = mapImage.getWidth();
		height = mapImage.getHeight();

		backgroundTileTypes = new int[width][height];
		wallTypes = new int[width][height];

		backgroundTiles = new Vector2[width][height];
		walls = new Vector2[width][height];
	}

	private void loadMap(String fileLocation) {
		try {
			mapImage = ImageIO.read(getClass().getResource(fileLocation));
		} catch (IOException e) {
			Logger.error(e);
		}
	}

	public void generateTiles() {
		setTileTypes();
		setTiles();
	}

	// fill the tile type arrays
	private void setTileTypes() {
		// take the map image and use it to fill the tile type array
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				switch (mapImage.getRGB(x, y)) {
				case GRASS_COLOR:
					backgroundTileTypes[x][y] = 1;
					break;
				case PATH_COLOR:
					backgroundTileTypes[x][y] = 2;
					break;
				case BROWNWALL_COLOR:
				case GREYWALL_COLOR:
					wallTypes[x][y] = 1;
					break;
				case LIGHTWATER_COLOR:
				case DARKWATER_COLOR:
					wallTypes[x][y] = 2;
					break;
				default:
					Logger.warn("Unknown tile code: " + mapImage.getRGB(x, y));
				}
			}
		}
	}

	private void setTiles() {
		// Set Background Tiles
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				switch (backgroundTileTypes[x][y]) {
				case GRASS:
					backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case PATH:
					backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
				}
			}
		}

		// insert correct wall/water type and side
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				switch (wallTypes[x][y]) {
				case WALL:
					walls[x][y] = new Vector2(1.0f, 2.0f);
					break;
				case WATER:
					walls[x][y] = new Vector2(1.0f, 1.0f);
				}
			}
		}
	}

	public Vector2[][] getBackgroundTiles() {
		return backgroundTiles;
	}

	public Vector2[][] getWalls() {
		return walls;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
