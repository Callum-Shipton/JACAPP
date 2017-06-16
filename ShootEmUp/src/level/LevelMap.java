package level;

import java.util.Map;

import ai.GoalBounder;
import display.Art;
import display.IRenderer;
import logging.Logger;
import math.Vector2;
import object.Entity;

public class LevelMap {

	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;

	// size of map
	private int width;
	private int height;

	private String file;

	// texture map position arrays
	private Vector2[][] backgroundTiles; // Replace with Irenderer changes
	private Vector2[][] wallTiles; // ^^
	private Vector2[][] foregroundTiles; // ^^

	// collidable wall entities
	private Map<Vector2, Entity> walls;

	private GoalBounder goalBounder;

	private MapGenerator generator;

	public LevelMap(String file) {
		this.file = file;

		generator = new MapGenerator(file);

		width = generator.getWidth();
		height = generator.getHeight();
	}

	public void init() {

		generator.generateMap();

		backgroundTiles = generator.getBackgroundTiles();
		wallTiles = generator.getWallTiles();
		foregroundTiles = generator.getForegroundTiles();
		walls = generator.getWalls();

		Art.irBack = new IRenderer(backgroundTiles,
				new Vector2(Art.getImage("Floor").getFWidth(), Art.getImage("Floor").getFHeight()),
				LevelMap.getTileWidth(), LevelMap.getTileHeight());
		Art.irWall = new IRenderer(walls,
				new Vector2(Art.getImage("Walls").getFWidth(), Art.getImage("Walls").getFHeight()),
				LevelMap.getTileWidth(), LevelMap.getTileHeight());
		Art.irFore = new IRenderer(foregroundTiles,
				new Vector2(Art.getImage("Walls").getFWidth(), Art.getImage("Walls").getFHeight()),
				LevelMap.getTileWidth(), LevelMap.getTileHeight());

		setGoalBounder(new GoalBounder(width, height, walls));

		Logger.info("Map Loaded");
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

	public static int getTileHeight() {
		return TILE_HEIGHT;
	}

	public static int getTileWidth() {
		return TILE_WIDTH;
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

	public Map<Vector2, Entity> getWalls() {
		return this.walls;
	}

	// Getters

	public Vector2[][] getWallTiles() {
		return this.wallTiles;
	}

	public int getWidth() {
		return this.width;
	}

}
