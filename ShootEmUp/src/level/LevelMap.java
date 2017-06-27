package level;

import java.util.HashMap;
import java.util.Map;

import ai.GoalBounder;
import components.collision.RigidCollision;
import components.graphical.MapGraphics;
import display.FloorRenderer;
import display.ImageProcessor;
import display.WallsRenderer;
import entity.Entity;
import logging.Logger;
import logging.Logger.Category;
import main.ShootEmUp;
import map.TileMap;
import math.Vector2;

public class LevelMap {

	public static final int TILE_WIDTH = 32;

	private TileMap tileMap;
	private GoalBounder goalBounder;
	private static final String WALLS_TEXTURE_FILE = "Walls";
	private static final String FLOOR_TEXTURE_FILE = "Floor";

	// collidable wall entities
	private Map<Vector2, Entity> walls;

	public LevelMap(String file) {
		tileMap = new TileMap(file);
		walls = new HashMap<>();

		String goalboundfile = file.substring(0, file.length() - 4) + ".bound";
		Logger.debug(goalboundfile, Category.AI_GOALBOUNDING);
		goalBounder = GoalBounder.readGoalbounder(goalboundfile);
	}

	public void init() {

		tileMap.init();

		ImageProcessor.irBack = new FloorRenderer(tileMap.getBackgroundTiles(),
				new Vector2(ImageProcessor.getImage(FLOOR_TEXTURE_FILE).getFWidth(),
						ImageProcessor.getImage(FLOOR_TEXTURE_FILE).getFHeight()),
				LevelMap.TILE_WIDTH, TILE_WIDTH);

		createWalls(tileMap.getWalls());
		ImageProcessor.irWall = new WallsRenderer(walls,
				new Vector2(ImageProcessor.getImage(WALLS_TEXTURE_FILE).getFWidth(),
						ImageProcessor.getImage(WALLS_TEXTURE_FILE).getFHeight()),
				LevelMap.TILE_WIDTH, TILE_WIDTH);

		/*
		 * ImageProcessor.irFore = new
		 * FloorRenderer(generator.getForegroundTiles(), new
		 * Vector2(ImageProcessor.getImage("Walls").getFWidth(),
		 * ImageProcessor.getImage("Walls").getFHeight()),
		 * LevelMap.getTileWidth(), LevelMap.getTileHeight());
		 */

		ImageProcessor.irBack.init();
		ImageProcessor.irWall.init();
		// ImageProcessor.irFore.init();

		Logger.info("Map Loaded");
	}

	public void renderHighTiles() {
		// ImageProcessor.irFore.draw(ImageProcessor.getImage("Walls").getID());
	}

	public void renderLowTiles() {
		ImageProcessor.irBack.draw(ImageProcessor.getImage(FLOOR_TEXTURE_FILE).getID());
		ImageProcessor.irWall.draw(ImageProcessor.getImage(WALLS_TEXTURE_FILE).getID());
	}

	private void createWalls(Vector2[][] walls) {
		for (int y = 0; y < walls.length; y++) {
			for (int x = 0; x < walls[0].length; x++) {

				Vector2 wall = walls[x][y];
				if (wall != null) {
					if (wall.equals(new Vector2(1.0f, 2.0f))) {
						insertWall(x, y, wall.x(), wall.y());
					} else if (wall.equals(new Vector2(1.0f, 1.0f))) {
						insertWater(x, y, wall.x(), wall.y());
					}
				}
			}
		}
	}

	// create wall
	private void insertWall(int x, int y, float tileMapX, float tileMapY) {
		MapGraphics wallG;
		wallG = new MapGraphics(ImageProcessor.getImage(WALLS_TEXTURE_FILE), new Vector2(tileMapX, tileMapY),
				x * TILE_WIDTH, y * TILE_WIDTH);

		createEntity(wallG, x, y);
	}

	// create water
	private void insertWater(int x, int y, float tileMapX, float tileMapY) {
		MapGraphics wallG;
		wallG = new MapGraphics(ImageProcessor.getImage(WALLS_TEXTURE_FILE), new Vector2(tileMapX, tileMapY + 4.0f),
				x * TILE_WIDTH, y * TILE_WIDTH);
		createEntity(wallG, x, y);
	}

	private void createEntity(MapGraphics wallG, int x, int y) {
		Entity wall = new Entity();
		wall.addComponent(wallG);
		RigidCollision rigidCollision = new RigidCollision();
		wall.addComponent(rigidCollision);
		ShootEmUp.getCurrentLevel().addEntity(wall);
		walls.put(new Vector2(x, y), wall);
	}

	// Getters

	public int getWidth() {
		return tileMap.getWidth();
	}

	public GoalBounder getGoalBounder() {
		return goalBounder;
	}

	public int getHeight() {
		return tileMap.getHeight();
	}

	public Map<Vector2, Entity> getWalls() {
		return walls;
	}
}
