package level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import components.collision.RigidCollision;
import components.control.GoalBounder;
import components.graphical.MapGraphics;
import display.Art;
import display.IRenderer;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class Map {

	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;

	// RGB integer values for tiles
	private final int BROWNWALL = -7864299;
	private final int GREYWALL = -8421505;
	private final int LIGHTWATER = -16735512;
	private final int DARKWATER = -12629812;
	private final int GRASS = -4856291;
	private final int PATH = -1055568;

	// size of map
	private int width;
	private int height;

	// map layout image
	private String file;
	private BufferedImage map = null;

	// Tile type arrays
	private int[][] backgroundTileTypes; // Replace with Irenderer changes
	private int[][] wallTileTypes; // ^^
	private int[][] foregroundTileTypes;

	// texture map position arrays
	private Vector2[][] backgroundTiles; // Replace with Irenderer changes
	private Vector2[][] wallTiles; // ^^
	private Vector2[][] foregroundTiles; // ^^

	// collidable wall entities
	public HashMap<Vector2, Entity> walls;
	public GoalBounder goalBounder;

	public Map(String file) {

		this.file = file;
		loadMap();
		width = map.getWidth();
		height = map.getHeight();
		backgroundTileTypes = new int[map.getWidth()][map.getHeight()];
		wallTileTypes = new int[map.getWidth()][map.getHeight()];
		foregroundTileTypes = new int[map.getWidth()][map.getHeight()];
		backgroundTiles = new Vector2[map.getWidth()][map.getHeight()];
		wallTiles = new Vector2[map.getWidth()][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth()][map.getHeight()];
		walls = new HashMap<Vector2, Entity>();
	}

	// Load in the level file
	private void loadMap() {
		try {
			map = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
		}
	}

	public void init(){
		
		setTileTypes();
		
		generateWalls();

		setTiles();

		Art.irBack = new IRenderer(backgroundTiles,
				new Vector2(Art.getImage("Floor").getFWidth(), Art.getImage("Floor").getFHeight()), Map.TILE_WIDTH,
				Map.TILE_HEIGHT);
		Art.irWall = new IRenderer(walls,
				new Vector2(Art.getImage("Walls").getFWidth(), Art.getImage("Walls").getFHeight()), Map.TILE_WIDTH,
				Map.TILE_HEIGHT);
		Art.irFore = new IRenderer(foregroundTiles,
				new Vector2(Art.getImage("Walls").getFWidth(), Art.getImage("Walls").getFHeight()), Map.TILE_WIDTH,
				Map.TILE_HEIGHT);
		
		goalBounder = new GoalBounder(width, height, walls);
	}
	
	private void generateWalls(){
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
						wallTileTypes[x][y] = 7;
						break;
					case GREYWALL:
						wallTileTypes[x][y] = 7;
						break;
					case LIGHTWATER:
						wallTileTypes[x][y] = 12;
						break;
					case DARKWATER:
						wallTileTypes[x][y] = 12;
						break;
					default:
						System.out.println(map.getRGB(x, y));
				}
			}
		}
	}

	public void setTiles() {
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
						insertWall(x, y, 0.0f, 2.0f, map.getRGB(x, y));
						break;
					case 2:
						insertWall(x, y, 1.0f, 2.0f, map.getRGB(x, y));
						break;
					case 3:
						insertWall(x, y, 2.0f, 2.0f, map.getRGB(x, y));
						break;
					case 4:
						insertWall(x, y, 0.0f, 3.0f, map.getRGB(x, y));
						break;
					case 5:
						insertWall(x, y, 1.0f, 3.0f, map.getRGB(x, y));
						break;
					case 6:
						insertWall(x, y, 2.0f, 3.0f, map.getRGB(x, y));
						break;
					case 7:
						insertWall(x, y, 0.0f, 1.0f, map.getRGB(x, y));
						break;
					case 8:
						insertWall(x, y, 1.0f, 1.0f, map.getRGB(x, y));
						break;
					case 9:
						insertWall(x, y, 2.0f, 1.0f, map.getRGB(x, y));
						break;
					case 10:
						insertWall(x, y, 0.0f, 0.0f, map.getRGB(x, y));
						break;
					case 11:
						insertWall(x, y, 2.0f, 0.0f, map.getRGB(x, y));
						break;
					case 12:
						insertWater(x, y, 0.0f, 0.0f, map.getRGB(x, y));
						break;
					case 13:
						insertWater(x, y, 1.0f, 0.0f, map.getRGB(x, y));
						break;
					case 14:
						insertWater(x, y, 2.0f, 0.0f, map.getRGB(x, y));
						break;
					case 15:
						insertWater(x, y, 0.0f, 1.0f, map.getRGB(x, y));
						break;
					case 16:
						insertWater(x, y, 1.0f, 1.0f, map.getRGB(x, y));
						break;
					case 17:
						insertWater(x, y, 2.0f, 1.0f, map.getRGB(x, y));
						break;
					case 18:
						insertWater(x, y, 0.0f, 2.0f, map.getRGB(x, y));
						break;
					case 19:
						insertWater(x, y, 1.0f, 2.0f, map.getRGB(x, y));
						break;
					case 20:
						insertWater(x, y, 2.0f, 2.0f, map.getRGB(x, y));
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
	public void insertWall(int x, int y, float tileMapX, float tileMapY, int wallType) {
		MapGraphics wallG;
		switch (wallType) {
			case BROWNWALL:
				wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY), x * Map.TILE_WIDTH,
						y * Map.TILE_HEIGHT);
				break;
			case GREYWALL:
				wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX + 3.0f, tileMapY),
						x * Map.TILE_WIDTH, y * Map.TILE_HEIGHT);
				break;
			default:
				wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY), x * Map.TILE_WIDTH,
						y * Map.TILE_HEIGHT);
				break;
		}

		Entity wall = new Entity();
		wall.addComponent(wallG);
		RigidCollision MC = new RigidCollision(wall, wallG);
		wall.addComponent(MC);
		ShootEmUp.currentLevel.entities.add(wall);
		walls.put(new Vector2(x, y), wall);
	}

	// create water
	public void insertWater(int x, int y, float tileMapX, float tileMapY, int waterType) {
		MapGraphics wallG;
		switch (map.getRGB(x, y)) {
			case LIGHTWATER:
				wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY + 4.0f),
						x * Map.TILE_WIDTH, y * Map.TILE_HEIGHT);
				break;
			case DARKWATER:
				wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX + 3.0f, tileMapY + 4.0f),
						x * Map.TILE_WIDTH, y * Map.TILE_HEIGHT);
				break;
			default:
				wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY + 4.0f),
						x * Map.TILE_WIDTH, y * Map.TILE_HEIGHT);
		}

		Entity wall = new Entity();
		wall.addComponent(wallG);
		RigidCollision MC = new RigidCollision(wall, wallG);
		wall.addComponent(MC);
		ShootEmUp.currentLevel.entities.add(wall);
		walls.put(new Vector2(x, y), wall);
	}

	public void renderLowTiles() {
		Art.irBack.draw(Art.getImage("Floor").getID());
		Art.irWall.draw(Art.getImage("Walls").getID());
	}

	public void renderHighTiles() {
		Art.irFore.draw(Art.getImage("Walls").getID());
	}

	// Getters

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2[][] getBackgroundTiles() {
		return backgroundTiles;
	}

	public Vector2[][] getWallTiles() {
		return wallTiles;
	}

	public Vector2[][] getForegroundTiles() {
		return foregroundTiles;
	}

	public BufferedImage getMap() {
		return map;
	}

	public HashMap<Vector2, Entity> getWalls() {
		return walls;
	}
}
