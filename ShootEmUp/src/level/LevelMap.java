package level;

import java.util.Map;

import ai.GoalBounder;
import display.FloorRenderer;
import display.ImageProcessor;
import display.WallsRenderer;
import entity.Entity;
import logging.Logger;
import math.Vector2;

public class LevelMap {

	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;

	// size of map
	private int width;
	private int height;

	// texture map position arrays
	private Vector2[][] backgroundTiles; // Replace with Irenderer changes

	// collidable wall entities
	private Map<Vector2, Entity> walls;

	private GoalBounder goalBounder;

	private MapGenerator generator;

	public LevelMap(String file) {
		generator = new MapGenerator(file);

		width = generator.getWidth();
		height = generator.getHeight();
	}

	public void init() {

		generator.generateMap();

		backgroundTiles = generator.getBackgroundTiles();
		Vector2[][] foregroundTiles = generator.getForegroundTiles();
		walls = generator.getWalls();

		ImageProcessor.irBack = new FloorRenderer(backgroundTiles,
				new Vector2(ImageProcessor.getImage("Floor").getFWidth(),
						ImageProcessor.getImage("Floor").getFHeight()),
				LevelMap.getTileWidth(), LevelMap.getTileHeight());
		ImageProcessor.irWall = new WallsRenderer(walls,
				new Vector2(ImageProcessor.getImage("Walls").getFWidth(),
						ImageProcessor.getImage("Walls").getFHeight()),
				LevelMap.getTileWidth(), LevelMap.getTileHeight());
		ImageProcessor.irFore = new FloorRenderer(foregroundTiles,
				new Vector2(ImageProcessor.getImage("Walls").getFWidth(),
						ImageProcessor.getImage("Walls").getFHeight()),
				LevelMap.getTileWidth(), LevelMap.getTileHeight());

		ImageProcessor.irBack.init();
		ImageProcessor.irWall.init();
		ImageProcessor.irFore.init();

		setGoalBounder(new GoalBounder(width, height, walls.keySet()));

		Logger.info("Map Loaded");
	}

	public void renderHighTiles() {
		ImageProcessor.irFore.draw(ImageProcessor.getImage("Walls").getID());
	}

	public void renderLowTiles() {
		ImageProcessor.irBack.draw(ImageProcessor.getImage("Floor").getID());
		ImageProcessor.irWall.draw(ImageProcessor.getImage("Walls").getID());
	}

	public void setGoalBounder(GoalBounder goalBounder) {
		this.goalBounder = goalBounder;
	}

	// Getters

	public int getWidth() {
		return width;
	}

	public static int getTileHeight() {
		return TILE_HEIGHT;
	}

	public static int getTileWidth() {
		return TILE_WIDTH;
	}

	public Vector2[][] getBackgroundTiles() {
		return backgroundTiles;
	}

	public GoalBounder getGoalBounder() {
		return goalBounder;
	}

	public int getHeight() {
		return height;
	}

	public Map<Vector2, Entity> getWalls() {
		return walls;
	}
}
