package level;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joml.Vector2f;
import org.joml.Vector2i;

import ai.goalbounding.GoalBounder;
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
import loop.Loop;
import map.MapTile;
import map.TileMap;
import maze.Direction;
import maze.MazeTile;

public class LevelMap {

	public static final int TILE_WIDTH = 32;

	private final TileMap tileMap;
	private final GoalBounder goalBounder;
	private static final String TILES_TEXTURE_FILE = "Tiles";
	private static final Vector2f WATER_MODIFIER = new Vector2f(0.0f, 4.0f);
	private static final Vector2f GROUND_MODIFIER = new Vector2f(0.0f, 7.0f);

	private MazeTile mazeTile;

	// collidable wall entities
	private final Map<Vector2i, Entity> walls;

	public LevelMap(MazeTile mazeTile) {
		this.mazeTile = mazeTile;

		String file = ImageProcessor.LEVEL_FILE_LOCATION + "1.map";

		tileMap = TileMap.readTileMap(file);
		Loop.getDisplay().getCamera()
				.setLevelSize(new Vector2f(tileMap.getWidth() * TILE_WIDTH, tileMap.getHeight() * TILE_WIDTH));

		walls = new HashMap<>();

		String goalboundfile = file.substring(0, file.length() - 4) + ".bound";
		Logger.debug(goalboundfile, Category.AI_GOALBOUNDING);
		goalBounder = GoalBounder.readGoalbounder(goalboundfile);
	}

	public void init() {

		ImageProcessor.irBack = new FloorRenderer(tileMap.getBackgroundTiles(),
				new Vector2f(ImageProcessor.getImage(TILES_TEXTURE_FILE).getFWidth(),
						ImageProcessor.getImage(TILES_TEXTURE_FILE).getFHeight()),
				LevelMap.TILE_WIDTH, TILE_WIDTH);

		createWalls(tileMap.getWalls());
		ImageProcessor.irWall = new WallsRenderer(walls,
				new Vector2f(ImageProcessor.getImage(TILES_TEXTURE_FILE).getFWidth(),
						ImageProcessor.getImage(TILES_TEXTURE_FILE).getFHeight()),
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
		ImageProcessor.irBack.draw(ImageProcessor.getImage(TILES_TEXTURE_FILE).getID());
		ImageProcessor.irWall.draw(ImageProcessor.getImage(TILES_TEXTURE_FILE).getID());
	}

	private void createWalls(Set<MapTile> walls) {

		for (MapTile wall : walls) {
			int x = wall.getPosition().x;
			int y = wall.getPosition().y;
			Vector2f texture = wall.getTexture();
			switch (wall.getType()) {
			case WALL:
				insertWall(x, y, texture);
				break;
			case WATER:
				insertWater(x, y, texture);
				break;
			default:
			}
		}

		int mapWidthMiddle = tileMap.getWidth() / 2;
		int mapHeightMiddle = tileMap.getHeight() / 2;
		if (mazeTile.getAdjacentTile(Direction.N)) {
			insertTransporter(mapWidthMiddle - 2, 0, new Vector2f(0.0f, 7.0f), Direction.N);
			insertTransporter(mapWidthMiddle - 1, 0, new Vector2f(0.0f, 7.0f), Direction.N);
			insertTransporter(mapWidthMiddle, 0, new Vector2f(0.0f, 7.0f), Direction.N);
			insertTransporter(mapWidthMiddle + 1, 0, new Vector2f(0.0f, 7.0f), Direction.N);
			insertTransporter(mapWidthMiddle - 2, 1, new Vector2f(0.0f, 7.0f), Direction.N);
			insertTransporter(mapWidthMiddle - 1, 1, new Vector2f(0.0f, 7.0f), Direction.N);
			insertTransporter(mapWidthMiddle, 1, new Vector2f(0.0f, 7.0f), Direction.N);
			insertTransporter(mapWidthMiddle + 2, 1, new Vector2f(0.0f, 7.0f), Direction.N);
		} else {
			insertWall(mapWidthMiddle - 2, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 1, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 2, 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 2, 1, new Vector2f(1.0f, 2.0f));
		}
		if (mazeTile.getAdjacentTile(Direction.S)) {
			insertTransporter(mapWidthMiddle - 2, tileMap.getHeight() - 1, new Vector2f(0.0f, 7.0f), Direction.S);
			insertTransporter(mapWidthMiddle - 1, tileMap.getHeight() - 1, new Vector2f(0.0f, 7.0f), Direction.S);
			insertTransporter(mapWidthMiddle, tileMap.getHeight() - 1, new Vector2f(0.0f, 7.0f), Direction.S);
			insertTransporter(mapWidthMiddle + 1, tileMap.getHeight(), new Vector2f(0.0f, 7.0f), Direction.S);
			insertTransporter(mapWidthMiddle - 2, tileMap.getHeight() - 2, new Vector2f(0.0f, 7.0f), Direction.S);
			insertTransporter(mapWidthMiddle - 1, tileMap.getHeight() - 2, new Vector2f(0.0f, 7.0f), Direction.S);
			insertTransporter(mapWidthMiddle, tileMap.getHeight() - 2, new Vector2f(0.0f, 7.0f), Direction.S);
			insertTransporter(mapWidthMiddle + 2, tileMap.getHeight() - 2, new Vector2f(0.0f, 7.0f), Direction.S);
		} else {
			insertWall(mapWidthMiddle - 2, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 1, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 2, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 2, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
		}
		if (mazeTile.getAdjacentTile(Direction.W)) {
			insertTransporter(0, mapHeightMiddle - 2, new Vector2f(0.0f, 7.0f), Direction.W);
			insertTransporter(0, mapHeightMiddle - 1, new Vector2f(0.0f, 7.0f), Direction.W);
			insertTransporter(0, mapHeightMiddle, new Vector2f(0.0f, 7.0f), Direction.W);
			insertTransporter(0, mapHeightMiddle + 1, new Vector2f(0.0f, 7.0f), Direction.W);
			insertTransporter(1, mapHeightMiddle - 2, new Vector2f(0.0f, 7.0f), Direction.W);
			insertTransporter(1, mapHeightMiddle - 1, new Vector2f(0.0f, 7.0f), Direction.W);
			insertTransporter(1, mapHeightMiddle, new Vector2f(0.0f, 7.0f), Direction.W);
			insertTransporter(1, mapHeightMiddle + 1, new Vector2f(0.0f, 7.0f), Direction.W);
		} else {
			insertWall(0, mapHeightMiddle - 2, new Vector2f(1.0f, 2.0f));
			insertWall(0, mapHeightMiddle - 1, new Vector2f(1.0f, 2.0f));
			insertWall(0, mapHeightMiddle, new Vector2f(1.0f, 2.0f));
			insertWall(0, mapHeightMiddle + 1, new Vector2f(1.0f, 2.0f));
			insertWall(1, mapHeightMiddle - 2, new Vector2f(1.0f, 2.0f));
			insertWall(1, mapHeightMiddle - 1, new Vector2f(1.0f, 2.0f));
			insertWall(1, mapHeightMiddle, new Vector2f(1.0f, 2.0f));
			insertWall(1, mapHeightMiddle + 1, new Vector2f(1.0f, 2.0f));
		}
		if (mazeTile.getAdjacentTile(Direction.E)) {
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle - 2, new Vector2f(0.0f, 7.0f), Direction.E);
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle - 1, new Vector2f(0.0f, 7.0f), Direction.E);
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle, new Vector2f(0.0f, 7.0f), Direction.E);
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle + 1, new Vector2f(0.0f, 7.0f), Direction.E);
			insertTransporter(tileMap.getHeight() - 2, mapHeightMiddle - 2, new Vector2f(0.0f, 7.0f), Direction.E);
			insertTransporter(tileMap.getHeight() - 2, mapHeightMiddle - 1, new Vector2f(0.0f, 7.0f), Direction.E);
			insertTransporter(tileMap.getHeight() - 2, mapHeightMiddle, new Vector2f(0.0f, 7.0f), Direction.E);
			insertTransporter(tileMap.getHeight() - 2, mapHeightMiddle + 1, new Vector2f(0.0f, 7.0f), Direction.E);
		} else {
			insertWall(tileMap.getHeight() - 1, mapHeightMiddle - 2, new Vector2f(1.0f, 2.0f));
			insertWall(tileMap.getHeight() - 1, mapHeightMiddle - 1, new Vector2f(1.0f, 2.0f));
			insertWall(tileMap.getHeight() - 1, mapHeightMiddle, new Vector2f(1.0f, 2.0f));
			insertWall(tileMap.getHeight() - 1, mapHeightMiddle + 1, new Vector2f(1.0f, 2.0f));
			insertWall(tileMap.getHeight() - 2, mapHeightMiddle - 2, new Vector2f(1.0f, 2.0f));
			insertWall(tileMap.getHeight() - 2, mapHeightMiddle - 1, new Vector2f(1.0f, 2.0f));
			insertWall(tileMap.getHeight() - 2, mapHeightMiddle, new Vector2f(1.0f, 2.0f));
			insertWall(tileMap.getHeight() - 2, mapHeightMiddle + 1, new Vector2f(1.0f, 2.0f));
		}
	}

	// create wall
	private void insertWall(int x, int y, Vector2f texture) {
		MapGraphics wallG = new MapGraphics(TILES_TEXTURE_FILE);
		wallG.setMapPos(texture);
		createEntity(wallG, x, y);
	}

	// create water
	private void insertWater(int x, int y, Vector2f texture) {
		MapGraphics wallG = new MapGraphics(TILES_TEXTURE_FILE);
		wallG.setMapPos(texture.add(WATER_MODIFIER));
		createEntity(wallG, x, y);
	}

	// create wall
	private void insertTransporter(int x, int y, Vector2f texture, Direction direction) {
		Entity wall = new Entity();
		MapGraphics wallG = new MapGraphics(TILES_TEXTURE_FILE);
		wallG.setMapPos(new Vector2f(GROUND_MODIFIER));
		wallG.setX(x * TILE_WIDTH);
		wallG.setY(y * TILE_WIDTH);
		wall.addComponent(wallG);
		BaseCollision collision = new TransportCollision(direction);
		wall.addComponent(collision);
		walls.put(new Vector2i(x, y), wall);
	}

	private void createEntity(MapGraphics wallG, int x, int y) {
		Entity wall = new Entity();
		wallG.setX(x * TILE_WIDTH);
		wallG.setY(y * TILE_WIDTH);
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
