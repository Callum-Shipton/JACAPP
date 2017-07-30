package map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Vector2f;

import logging.Logger;

public class TileGenerator {

	private BufferedImage mapImage;

	private final Vector2f[][] backgroundTiles;
	private final Vector2f[][] walls;
	private final int[][] backgroundTileTypes;
	private final int[][] wallTypes;

	// RGB integer values for tiles
	private static final int BROWNWALL_COLOR = -7864299;
	private static final int GREYWALL_COLOR = -8421505;
	private static final int LIGHTWATER_COLOR = -16735512;
	private static final int DARKWATER_COLOR = -12629812;

	private static final int GRASS_COLOR = -4856291;
	private static final int PATH_COLOR = -1055568;

	private static final int NEXT_LEVEL_COLOR = -6075996;
	private static final int PREVIOUS_LEVEL_COLOR = -20791;

	private static final int GRASS = 1;
	private static final int PATH = 2;

	private static final int WALL = 1;
	private static final int WATER = 2;
	private static final int NEXT_LEVEL = 3;
	private static final int PREVIOUS_LEVEL = 4;

	private final int width;
	private final int height;

	public TileGenerator(String fileLocation) {
		loadMap(fileLocation);
		width = mapImage.getWidth();
		height = mapImage.getHeight();

		backgroundTileTypes = new int[width][height];
		wallTypes = new int[width][height];

		backgroundTiles = new Vector2f[width][height];
		walls = new Vector2f[width][height];
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
				case NEXT_LEVEL_COLOR:
					wallTypes[x][y] = 3;
					break;
				case PREVIOUS_LEVEL_COLOR:
					wallTypes[x][y] = 4;
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
					backgroundTiles[x][y] = new Vector2f(0.0f, 7.0f);
					break;
				case PATH:
					backgroundTiles[x][y] = new Vector2f(1.0f, 7.0f);
				}
			}
		}

		// insert correct wall/water type and side
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				switch (wallTypes[x][y]) {
				case WALL:
					walls[x][y] = new Vector2f(1.0f, 2.0f);
					break;
				case WATER:
					walls[x][y] = new Vector2f(1.0f, 1.0f);
					break;
				case NEXT_LEVEL:
					walls[x][y] = new Vector2f(0.0f, 0.0f);
					break;
				case PREVIOUS_LEVEL:
					walls[x][y] = new Vector2f(1.0f, 0.0f);
				}
			}
		}
	}

	public Vector2f[][] getBackgroundTiles() {
		return backgroundTiles;
	}

	public Vector2f[][] getWalls() {
		return walls;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
