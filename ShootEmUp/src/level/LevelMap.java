package level;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector2i;

import ai.GoalBounder;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.collision.TransportCollision;
import components.graphical.MapGraphics;
import display.FloorRenderer;
import display.ImageProcessor;
import display.WallsRenderer;
import entity.Entity;
import logging.Logger;
import logging.Logger.Category;
import map.TileMap;

public class LevelMap {

	public static final int TILE_WIDTH = 32;

	private final TileMap tileMap;
	private final GoalBounder goalBounder;
	private static final String WALLS_TEXTURE_FILE = "Walls";
	private static final String FLOOR_TEXTURE_FILE = "Floor";

	// collidable wall entities
	private final Map<Vector2i, Entity> walls;

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
				new Vector2f(ImageProcessor.getImage(FLOOR_TEXTURE_FILE).getFWidth(),
						ImageProcessor.getImage(FLOOR_TEXTURE_FILE).getFHeight()),
				LevelMap.TILE_WIDTH, TILE_WIDTH);

		createWalls(tileMap.getWalls());
		ImageProcessor.irWall = new WallsRenderer(walls,
				new Vector2f(ImageProcessor.getImage(WALLS_TEXTURE_FILE).getFWidth(),
						ImageProcessor.getImage(WALLS_TEXTURE_FILE).getFHeight()),
				LevelMap.TILE_WIDTH, TILE_WIDTH);

		/*
		 * ImageProcessor.irFore = new FloorRenderer(generator.getForegroundTiles(), new
		 * Vector2(ImageProcessor.getImage("Walls").getFWidth(),
		 * ImageProcessor.getImage("Walls").getFHeight()), LevelMap.getTileWidth(),
		 * LevelMap.getTileHeight());
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

	private void createWalls(Vector2f[][] walls) {
		for (int y = 0; y < walls.length; y++) {
			for (int x = 0; x < walls[0].length; x++) {

				Vector2f wall = walls[x][y];
				if (wall != null) {
					if (wall.equals(new Vector2f(1.0f, 2.0f))) {
						insertWall(x, y, wall.x(), wall.y());
					} else if (wall.equals(new Vector2f(1.0f, 1.0f))) {
						insertWater(x, y, wall.x(), wall.y());
					} else if (wall.equals(new Vector2f(0.0f, 0.0f))) {
						insertTransporter(x, y, wall.x(), wall.y(), 1);
					} else if (wall.equals(new Vector2f(1.0f, 0.0f))) {
						insertTransporter(x, y, wall.x(), wall.y(), -1);
					}
				}
			}
		}
	}

	// create wall
	private void insertWall(int x, int y, float tileMapX, float tileMapY) {
		Entity wall = new Entity();
		MapGraphics wallG;
		wallG = new MapGraphics(WALLS_TEXTURE_FILE);
		wallG.setMapPos(new Vector2f(tileMapX, tileMapY));
		wallG.setX(x * TILE_WIDTH);
		wallG.setY(y * TILE_WIDTH);
		createEntity(wallG, x, y, wall);
	}

	// create water
	private void insertWater(int x, int y, float tileMapX, float tileMapY) {
		Entity water = new Entity();
		MapGraphics wallG;
		wallG = new MapGraphics(WALLS_TEXTURE_FILE);
		wallG.setMapPos(new Vector2f(tileMapX, tileMapY + 4.0f));
		wallG.setX(x * TILE_WIDTH);
		wallG.setY(y * TILE_WIDTH);
		createEntity(wallG, x, y, water);
	}

	// create wall
	private void insertTransporter(int x, int y, float tileMapX, float tileMapY, int direction) {
		Entity wall = new Entity();
		MapGraphics wallG;
		wallG = new MapGraphics(WALLS_TEXTURE_FILE);
		wallG.setMapPos(new Vector2f(tileMapX, tileMapY));
		wallG.setX(x * TILE_WIDTH);
		wallG.setY(y * TILE_WIDTH);
		wall.addComponent(wallG);
		BaseCollision collision = new TransportCollision(direction);
		wall.addComponent(collision);
		walls.put(new Vector2i(x, y), wall);
	}

	private void createEntity(MapGraphics wallG, int x, int y, Entity wall) {
		wall.addComponent(wallG);
		RigidCollision rigidCollision = new RigidCollision();
		wall.addComponent(rigidCollision);
		walls.put(new Vector2i(x, y), wall);
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

	public Map<Vector2i, Entity> getWalls() {
		return walls;
	}
}
