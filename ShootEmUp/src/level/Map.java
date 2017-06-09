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
import main.Logger;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class Map {

	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;

	public static int getTileHeight() {
		return TILE_HEIGHT;
	}

	public static int getTileWidth() {
		return TILE_WIDTH;
	}

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
	private HashMap<Vector2, Entity> walls;

	private GoalBounder goalBounder;

	public Map(String file) {

		this.file = file;
		loadMap();
		this.width = this.map.getWidth();
		this.height = this.map.getHeight();
		this.backgroundTileTypes = new int[this.map.getWidth()][this.map.getHeight()];
		this.wallTileTypes = new int[this.map.getWidth()][this.map.getHeight()];
		this.foregroundTileTypes = new int[this.map.getWidth()][this.map.getHeight()];
		this.backgroundTiles = new Vector2[this.map.getWidth()][this.map.getHeight()];
		this.wallTiles = new Vector2[this.map.getWidth()][this.map.getHeight()];
		this.foregroundTiles = new Vector2[this.map.getWidth()][this.map.getHeight()];
		this.walls = new HashMap<Vector2, Entity>();
	}

	private void generateWalls() {
		// Set Wall & Water tile types
		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				// if a wall
				if (this.wallTileTypes[x][y] == 7) {
					if (x > 0) {
						if ((this.wallTileTypes[x - 1][y] == 0) || (this.wallTileTypes[x - 1][y] > 11)) {
							if (y < (this.map.getHeight() - 2)) {
								if ((this.wallTileTypes[x][y + 2] == 0) || (this.wallTileTypes[x][y + 2] > 11)) {
									if ((this.wallTileTypes[x][y + 1] == 0) || (this.wallTileTypes[x][y + 1] > 11)) {
										this.wallTileTypes[x][y] = 4;
									} else {
										this.wallTileTypes[x][y] = 1;
									}
								}
							}
						}
						if ((this.wallTileTypes[x - 1][y] == 7) || (this.wallTileTypes[x - 1][y] == 8)) {
							if (y < (this.map.getHeight() - 2)) {
								if ((this.wallTileTypes[x][y + 2] == 0) || (this.wallTileTypes[x][y + 2] > 11)) {
									this.wallTileTypes[x][y] = 2;
									this.wallTileTypes[x - 1][y] = 9;
								} else {
									this.wallTileTypes[x][y] = 8;
								}
							}
						}
						if (x < (this.map.getWidth() - 1)) {
							if ((this.wallTileTypes[x + 1][y] == 0) || (this.wallTileTypes[x + 1][y] > 11)) {
								this.wallTileTypes[x][y] = 9;
							}
							if ((this.wallTileTypes[x - 1][y] == 1) || (this.wallTileTypes[x - 1][y] == 2)) {
								if (y < (this.map.getHeight() - 2)) {
									if ((this.wallTileTypes[x][y + 2] == 0) || (this.wallTileTypes[x][y + 2] > 11)) {
										if ((this.wallTileTypes[x + 1][y] == 0)
												|| (this.wallTileTypes[x + 1][y] > 11)) {
											this.wallTileTypes[x][y] = 3;
										} else {
											this.wallTileTypes[x][y] = 2;
										}
									}
								}
							}
						}
						if ((this.wallTileTypes[x - 1][y] == 4) || (this.wallTileTypes[x - 1][y] == 5)) {
							if (y < (this.map.getHeight() - 1)) {
								if ((this.wallTileTypes[x][y + 1] == 0) || (this.wallTileTypes[x][y + 1] > 11)) {
									this.wallTileTypes[x][y] = 5;
								}
							}
							if (x < (this.map.getWidth() - 1)) {
								if ((this.wallTileTypes[x + 1][y] == 0) || (this.wallTileTypes[x + 1][y] > 11)) {
									this.wallTileTypes[x][y] = 6;
								}
							}
						}
					}
				}
				// if water
				if (this.wallTileTypes[x][y] == 12) {
					this.wallTileTypes[x][y] = 16;
				}
			}
		}
		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				if (this.wallTileTypes[x][y] == 0) {
					if (y < (this.map.getHeight() - 1)) {
						if (this.wallTileTypes[x][y + 1] == 1) {
							this.foregroundTileTypes[x][y] = 1;
						} else if (this.wallTileTypes[x][y + 1] == 2) {
							this.foregroundTileTypes[x][y] = 2;
							if ((x > 0) && (x < (this.map.getWidth() - 1))) {
								if (this.foregroundTileTypes[x - 1][y] == 3) {
									this.foregroundTileTypes[x - 1][y] = 2;
								}
								if (this.wallTileTypes[x - 1][y] == 9) {
									this.wallTileTypes[x - 1][y] = 8;
								}
								if (this.wallTileTypes[x + 1][y] == 7) {
									this.wallTileTypes[x + 1][y] = 8;
								}
							}
						} else if (this.wallTileTypes[x][y + 1] == 3) {
							this.foregroundTileTypes[x][y] = 3;
						}
						if ((x > 0) && (x < (this.map.getWidth() - 1))) {
							if (this.wallTileTypes[x][y + 1] == 7) {
								if (this.foregroundTileTypes[x - 1][y] != 0) {
									this.foregroundTileTypes[x][y] = 2;
								} else {
									this.foregroundTileTypes[x][y] = 1;
								}
							} else if (this.wallTileTypes[x][y + 1] == 9) {
								this.foregroundTileTypes[x][y] = 3;
							}
						}
					}
				}
			}
		}
	}

	public Vector2[][] getBackgroundTiles() {
		return this.backgroundTiles;
	}

	public Vector2[][] getForegroundTiles() {
		return this.foregroundTiles;
	}

	public GoalBounder getGoalBounder() {
		return this.goalBounder;
	}

	public int getHeight() {
		return this.height;
	}

	public BufferedImage getMap() {
		return this.map;
	}

	public HashMap<Vector2, Entity> getWalls() {
		return this.walls;
	}

	// Getters

	public Vector2[][] getWallTiles() {
		return this.wallTiles;
	}

	public int getWidth() {
		return this.width;
	}

	public void init() {

		setTileTypes();

		generateWalls();

		setTiles();

		Art.irBack = new IRenderer(this.backgroundTiles,
				new Vector2(Art.getImage("Floor").getFWidth(), Art.getImage("Floor").getFHeight()), Map.getTileWidth(),
				Map.getTileHeight());
		Art.irWall = new IRenderer(this.walls,
				new Vector2(Art.getImage("Walls").getFWidth(), Art.getImage("Walls").getFHeight()), Map.getTileWidth(),
				Map.getTileHeight());
		Art.irFore = new IRenderer(this.foregroundTiles,
				new Vector2(Art.getImage("Walls").getFWidth(), Art.getImage("Walls").getFHeight()), Map.getTileWidth(),
				Map.getTileHeight());

		setGoalBounder(new GoalBounder(this.width, this.height, this.walls));
	}

	// create wall
	private void insertWall(int x, int y, float tileMapX, float tileMapY, int wallType) {
		MapGraphics wallG;
		switch (wallType) {
		case BROWNWALL:
			wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY), x * Map.getTileWidth(),
					y * Map.getTileHeight());
			break;
		case GREYWALL:
			wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX + 3.0f, tileMapY),
					x * Map.getTileWidth(), y * Map.getTileHeight());
			break;
		default:
			wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY), x * Map.getTileWidth(),
					y * Map.getTileHeight());
			break;
		}

		Entity wall = new Entity();
		wall.addComponent(wallG);
		RigidCollision MC = new RigidCollision(wall, wallG);
		wall.addComponent(MC);
		ShootEmUp.getCurrentLevel().addEntity(wall);
		this.walls.put(new Vector2(x, y), wall);
	}

	// create water
	private void insertWater(int x, int y, float tileMapX, float tileMapY, int waterType) {
		MapGraphics wallG;
		switch (this.map.getRGB(x, y)) {
		case LIGHTWATER:
			wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY + 4.0f),
					x * Map.getTileWidth(), y * Map.getTileHeight());
			break;
		case DARKWATER:
			wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX + 3.0f, tileMapY + 4.0f),
					x * Map.getTileWidth(), y * Map.getTileHeight());
			break;
		default:
			wallG = new MapGraphics(Art.getImage("Walls"), new Vector2(tileMapX, tileMapY + 4.0f),
					x * Map.getTileWidth(), y * Map.getTileHeight());
		}

		Entity wall = new Entity();
		wall.addComponent(wallG);
		RigidCollision MC = new RigidCollision(wall, wallG);
		wall.addComponent(MC);
		ShootEmUp.getCurrentLevel().addEntity(wall);
		this.walls.put(new Vector2(x, y), wall);
	}

	// Load in the level file
	private void loadMap() {
		try {
			this.map = ImageIO.read(getClass().getResource(this.file));
		} catch (IOException e) {
		}
	}

	public void renderHighTiles() {
		Art.irFore.draw(Art.getImage("Walls").getID());
	}

	public void renderLowTiles() {
		Art.irBack.draw(Art.getImage("Floor").getID());
		Art.irWall.draw(Art.getImage("Walls").getID());
	}

	public void setGoalBounder(GoalBounder goalBounder) {
		this.goalBounder = goalBounder;
	}

	private void setTiles() {
		// Set Background Tiles
		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				switch (this.backgroundTileTypes[x][y]) {
				case 1:
					this.backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case 2:
					this.backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				}
			}
		}
		// insert correct wall/water type and side
		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				switch (this.wallTileTypes[x][y]) {
				case 1:
					insertWall(x, y, 0.0f, 2.0f, this.map.getRGB(x, y));
					break;
				case 2:
					insertWall(x, y, 1.0f, 2.0f, this.map.getRGB(x, y));
					break;
				case 3:
					insertWall(x, y, 2.0f, 2.0f, this.map.getRGB(x, y));
					break;
				case 4:
					insertWall(x, y, 0.0f, 3.0f, this.map.getRGB(x, y));
					break;
				case 5:
					insertWall(x, y, 1.0f, 3.0f, this.map.getRGB(x, y));
					break;
				case 6:
					insertWall(x, y, 2.0f, 3.0f, this.map.getRGB(x, y));
					break;
				case 7:
					insertWall(x, y, 0.0f, 1.0f, this.map.getRGB(x, y));
					break;
				case 8:
					insertWall(x, y, 1.0f, 1.0f, this.map.getRGB(x, y));
					break;
				case 9:
					insertWall(x, y, 2.0f, 1.0f, this.map.getRGB(x, y));
					break;
				case 10:
					insertWall(x, y, 0.0f, 0.0f, this.map.getRGB(x, y));
					break;
				case 11:
					insertWall(x, y, 2.0f, 0.0f, this.map.getRGB(x, y));
					break;
				case 12:
					insertWater(x, y, 0.0f, 0.0f, this.map.getRGB(x, y));
					break;
				case 13:
					insertWater(x, y, 1.0f, 0.0f, this.map.getRGB(x, y));
					break;
				case 14:
					insertWater(x, y, 2.0f, 0.0f, this.map.getRGB(x, y));
					break;
				case 15:
					insertWater(x, y, 0.0f, 1.0f, this.map.getRGB(x, y));
					break;
				case 16:
					insertWater(x, y, 1.0f, 1.0f, this.map.getRGB(x, y));
					break;
				case 17:
					insertWater(x, y, 2.0f, 1.0f, this.map.getRGB(x, y));
					break;
				case 18:
					insertWater(x, y, 0.0f, 2.0f, this.map.getRGB(x, y));
					break;
				case 19:
					insertWater(x, y, 1.0f, 2.0f, this.map.getRGB(x, y));
					break;
				case 20:
					insertWater(x, y, 2.0f, 2.0f, this.map.getRGB(x, y));
					break;
				}
			}
		}
		// Set Foreground Tiles
		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				switch (this.foregroundTileTypes[x][y]) {
				case 1:
					this.foregroundTiles[x][y] = new Vector2(0.0f, 1.0f);
					break;
				case 2:
					this.foregroundTiles[x][y] = new Vector2(1.0f, 1.0f);
					break;
				case 3:
					this.foregroundTiles[x][y] = new Vector2(2.0f, 1.0f);
					break;
				case 4:
					this.foregroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case 5:
					this.foregroundTiles[x][y] = new Vector2(2.0f, 0.0f);
					break;
				}
			}
		}
	}

	// fill the tile type arrays
	private void setTileTypes() {
		// take the map image and use it to fill the tile type array
		for (int y = 0; y < this.map.getHeight(); y++) {
			for (int x = 0; x < this.map.getWidth(); x++) {
				switch (this.map.getRGB(x, y)) {
				case GRASS:
					this.backgroundTileTypes[x][y] = 1;
					break;
				case PATH:
					this.backgroundTileTypes[x][y] = 2;
					break;
				case BROWNWALL:
					this.wallTileTypes[x][y] = 7;
					break;
				case GREYWALL:
					this.wallTileTypes[x][y] = 7;
					break;
				case LIGHTWATER:
					this.wallTileTypes[x][y] = 12;
					break;
				case DARKWATER:
					this.wallTileTypes[x][y] = 12;
					break;
				default:
					Logger.warn("Unknown tile code: " + this.map.getRGB(x, y));
				}
			}
		}
	}
}
