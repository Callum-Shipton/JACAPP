package level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import components.collision.RigidCollision;
import components.graphical.MapGraphics;
import display.Art;
import main.Logger;
import main.Loop;
import math.Vector2;
import object.Entity;

public class MapGenerator {

	private BufferedImage map = null;

	private static final String TEXTURE_FILE = "Walls";

	// size of map
	private int width;
	private int height;

	// RGB integer values for tiles
	private static final int BROWNWALL = -7864299;
	private static final int GREYWALL = -8421505;
	private static final int LIGHTWATER = -16735512;
	private static final int DARKWATER = -12629812;

	private static final int GRASS = -4856291;
	private static final int PATH = -1055568;

	// Tile type arrays
	private int[][] backgroundTileTypes; // Replace with Irenderer changes
	private int[][] wallTileTypes; // ^^
	private int[][] foregroundTileTypes;

	private Vector2[][] backgroundTiles; // Replace with Irenderer changes
	private Vector2[][] foregroundTiles; // ^^

	// collidable wall entities
	private Map<Vector2, Entity> walls;

	public MapGenerator(String file) {
		loadMap(file);
		width = map.getWidth();
		height = map.getHeight();
		backgroundTileTypes = new int[width][height];
		wallTileTypes = new int[width][height];
		foregroundTileTypes = new int[width][height];
		backgroundTiles = new Vector2[width][height];
		foregroundTiles = new Vector2[width][height];
		walls = new HashMap<>();
	}

	public void generateMap() {
		setTileTypes();

		// generateWalls();

		setTiles();
	}

	// Load in the level file
	private void loadMap(String file) {
		try {
			map = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
			Logger.error(e);
		}

	}

	// fill the tile type arrays
	private void setTileTypes() {
		// take the map image and use it to fill the tile type array
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (map.getRGB(x, y)) {
				case GRASS:
					backgroundTileTypes[x][y] = 1;
					break;
				case PATH:
					backgroundTileTypes[x][y] = 2;
					break;
				case BROWNWALL:
				case GREYWALL:
					wallTileTypes[x][y] = 7;
					break;
				case LIGHTWATER:
				case DARKWATER:
					wallTileTypes[x][y] = 12;
					break;
				default:
					Logger.warn("Unknown tile code: " + map.getRGB(x, y));
				}
			}
		}
	}

	private void generateWalls() {
		// Set Wall & Water tile types
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				// if a wall
				if (wallTileTypes[x][y] == 7) {
					if (x > 0) {
						if ((wallTileTypes[x - 1][y] == 0) || (wallTileTypes[x - 1][y] > 11)) {
							if (y < (map.getHeight() - 2)) {
								if ((wallTileTypes[x][y + 2] == 0) || (wallTileTypes[x][y + 2] > 11)) {
									if ((wallTileTypes[x][y + 1] == 0) || (wallTileTypes[x][y + 1] > 11)) {
										wallTileTypes[x][y] = 4;
									} else {
										wallTileTypes[x][y] = 1;
									}
								}
							}
						}
						if ((wallTileTypes[x - 1][y] == 7) || (wallTileTypes[x - 1][y] == 8)) {
							if (y < (map.getHeight() - 2)) {
								if ((wallTileTypes[x][y + 2] == 0) || (wallTileTypes[x][y + 2] > 11)) {
									wallTileTypes[x][y] = 2;
									wallTileTypes[x - 1][y] = 9;
								} else {
									wallTileTypes[x][y] = 8;
								}
							}
						}
						if (x < (map.getWidth() - 1)) {
							if ((wallTileTypes[x + 1][y] == 0) || (wallTileTypes[x + 1][y] > 11)) {
								wallTileTypes[x][y] = 9;
							}
							if ((wallTileTypes[x - 1][y] == 1) || (wallTileTypes[x - 1][y] == 2)) {
								if (y < (map.getHeight() - 2)) {
									if ((wallTileTypes[x][y + 2] == 0) || (wallTileTypes[x][y + 2] > 11)) {
										if ((wallTileTypes[x + 1][y] == 0) || (wallTileTypes[x + 1][y] > 11)) {
											wallTileTypes[x][y] = 3;
										} else {
											wallTileTypes[x][y] = 2;
										}
									}
								}
							}
						}
						if ((wallTileTypes[x - 1][y] == 4) || (wallTileTypes[x - 1][y] == 5)) {
							if (y < (map.getHeight() - 1)) {
								if ((wallTileTypes[x][y + 1] == 0) || (wallTileTypes[x][y + 1] > 11)) {
									wallTileTypes[x][y] = 5;
								}
							}
							if (x < (map.getWidth() - 1)) {
								if ((wallTileTypes[x + 1][y] == 0) || (wallTileTypes[x + 1][y] > 11)) {
									wallTileTypes[x][y] = 6;
								}
							}
						}
					}
				}
				// if water
				if (wallTileTypes[x][y] == 12) {
					wallTileTypes[x][y] = 16;
				}
			}
		}
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				if (wallTileTypes[x][y] == 0) {
					if (y < (map.getHeight() - 1)) {
						if (wallTileTypes[x][y + 1] == 1) {
							foregroundTileTypes[x][y] = 1;
						} else if (wallTileTypes[x][y + 1] == 2) {
							foregroundTileTypes[x][y] = 2;
							if ((x > 0) && (x < (map.getWidth() - 1))) {
								if (foregroundTileTypes[x - 1][y] == 3) {
									foregroundTileTypes[x - 1][y] = 2;
								}
								if (wallTileTypes[x - 1][y] == 9) {
									wallTileTypes[x - 1][y] = 8;
								}
								if (wallTileTypes[x + 1][y] == 7) {
									wallTileTypes[x + 1][y] = 8;
								}
							}
						} else if (wallTileTypes[x][y + 1] == 3) {
							foregroundTileTypes[x][y] = 3;
						}
						if ((x > 0) && (x < (map.getWidth() - 1))) {
							if (wallTileTypes[x][y + 1] == 7) {
								if (foregroundTileTypes[x - 1][y] != 0) {
									foregroundTileTypes[x][y] = 2;
								} else {
									foregroundTileTypes[x][y] = 1;
								}
							} else if (wallTileTypes[x][y + 1] == 9) {
								foregroundTileTypes[x][y] = 3;
							}
						}
					}
				}
			}
		}
	}

	private void setTiles() {
		// Set Background Tiles
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (backgroundTileTypes[x][y]) {
				case 1:
					backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case 2:
					backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				}
			}
		}
		// insert correct wall/water type and side
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (wallTileTypes[x][y]) {
				case 1:
					insertWall(x, y, 0.0f, 2.0f);
					break;
				case 2:
					insertWall(x, y, 1.0f, 2.0f);
					break;
				case 3:
					insertWall(x, y, 2.0f, 2.0f);
					break;
				case 4:
					insertWall(x, y, 0.0f, 3.0f);
					break;
				case 5:
					insertWall(x, y, 1.0f, 3.0f);
					break;
				case 6:
					insertWall(x, y, 2.0f, 3.0f);
					break;
				case 7:
					insertWall(x, y, 0.0f, 1.0f);
					break;
				case 8:
					insertWall(x, y, 1.0f, 1.0f);
					break;
				case 9:
					insertWall(x, y, 2.0f, 1.0f);
					break;
				case 10:
					insertWall(x, y, 0.0f, 0.0f);
					break;
				case 11:
					insertWall(x, y, 2.0f, 0.0f);
					break;
				case 12:
					insertWater(x, y, 0.0f, 0.0f);
					break;
				case 13:
					insertWater(x, y, 1.0f, 0.0f);
					break;
				case 14:
					insertWater(x, y, 2.0f, 0.0f);
					break;
				case 15:
					insertWater(x, y, 0.0f, 1.0f);
					break;
				case 16:
					insertWater(x, y, 1.0f, 1.0f);
					break;
				case 17:
					insertWater(x, y, 2.0f, 1.0f);
					break;
				case 18:
					insertWater(x, y, 0.0f, 2.0f);
					break;
				case 19:
					insertWater(x, y, 1.0f, 2.0f);
					break;
				case 20:
					insertWater(x, y, 2.0f, 2.0f);
					break;
				}
			}
		}
		// Set Foreground Tiles
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (foregroundTileTypes[x][y]) {
				case 1:
					foregroundTiles[x][y] = new Vector2(0.0f, 1.0f);
					break;
				case 2:
					foregroundTiles[x][y] = new Vector2(1.0f, 1.0f);
					break;
				case 3:
					foregroundTiles[x][y] = new Vector2(2.0f, 1.0f);
					break;
				case 4:
					foregroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case 5:
					foregroundTiles[x][y] = new Vector2(2.0f, 0.0f);
					break;
				}
			}
		}
	}

	// create wall
	private void insertWall(int x, int y, float tileMapX, float tileMapY) {
		MapGraphics wallG;
		switch (map.getRGB(x, y)) {
		case GREYWALL:
			wallG = new MapGraphics(Art.getImage(TEXTURE_FILE), new Vector2(tileMapX + 3.0f, tileMapY),
					x * LevelMap.getTileWidth(), y * LevelMap.getTileHeight());
			break;
		case BROWNWALL:
		default:
			wallG = new MapGraphics(Art.getImage(TEXTURE_FILE), new Vector2(tileMapX, tileMapY),
					x * LevelMap.getTileWidth(), y * LevelMap.getTileHeight());
			break;
		}

		createEntity(wallG, x, y);
	}

	// create water
	private void insertWater(int x, int y, float tileMapX, float tileMapY) {
		MapGraphics wallG;
		switch (this.map.getRGB(x, y)) {
		case LIGHTWATER:
			wallG = new MapGraphics(Art.getImage(TEXTURE_FILE), new Vector2(tileMapX, tileMapY + 4.0f),
					x * LevelMap.getTileWidth(), y * LevelMap.getTileHeight());
			break;
		case DARKWATER:
			wallG = new MapGraphics(Art.getImage(TEXTURE_FILE), new Vector2(tileMapX + 3.0f, tileMapY + 4.0f),
					x * LevelMap.getTileWidth(), y * LevelMap.getTileHeight());
			break;
		default:
			wallG = new MapGraphics(Art.getImage(TEXTURE_FILE), new Vector2(tileMapX, tileMapY + 4.0f),
					x * LevelMap.getTileWidth(), y * LevelMap.getTileHeight());
		}

		createEntity(wallG, x, y);
	}

	private void createEntity(MapGraphics wallG, int x, int y) {
		Entity wall = new Entity();
		wall.addComponent(wallG);
		RigidCollision rigidCollision = new RigidCollision();
		wall.addComponent(rigidCollision);
		Loop.getCurrentLevel().addEntity(wall);
		walls.put(new Vector2(x, y), wall);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2[][] getBackgroundTiles() {
		return backgroundTiles;
	}

	public Vector2[][] getForegroundTiles() {
		return foregroundTiles;
	}

	public Vector2[][] getWallTiles() {
		return foregroundTiles;
	}

	public Map<Vector2, Entity> getWalls() {
		return walls;
	}
}
