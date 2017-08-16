package level;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.joml.Vector2f;
import org.joml.Vector2i;

import ai.goalbounding.GoalBounder;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.collision.TransportCollision;
import components.graphical.MapGraphics;
import display.ArtLoader;
import display.FloorRenderer;
import display.BaseRenderSystem;
import display.WallsRenderer;
import entity.Entity;
import logging.Logger;
import logging.Logger.Category;
import loop.GameLoop;
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
	private MazeTile mazeTile;

	// collidable wall entities
	private final Map<Vector2i, Entity> walls;

	public LevelMap(MazeTile mazeTile) {
		this.mazeTile = mazeTile;

		Random rand = new Random();

		String file = BaseRenderSystem.LEVEL_FILE_LOCATION + (rand.nextInt(5) + 1) + ".map";

		tileMap = TileMap.readTileMap(file);
		GameLoop.getDisplay().getCamera()
				.setLevelSize(new Vector2f(tileMap.getWidth() * TILE_WIDTH, tileMap.getHeight() * TILE_WIDTH));

		walls = new HashMap<>();

		String goalboundfile = file.substring(0, file.length() - 4) + ".bound";
		Logger.debug(goalboundfile, Category.AI_GOALBOUNDING);
		goalBounder = GoalBounder.readGoalbounder(goalboundfile);
	}

	public void init() {

		BaseRenderSystem.irBack = new FloorRenderer(tileMap.getBackgroundTiles(),
				new Vector2f(ArtLoader.getImage(TILES_TEXTURE_FILE).getFWidth(),
						ArtLoader.getImage(TILES_TEXTURE_FILE).getFHeight()),
				LevelMap.TILE_WIDTH, TILE_WIDTH);

		createWalls(tileMap.getWalls());
		BaseRenderSystem.irWall = new WallsRenderer(walls,
				new Vector2f(ArtLoader.getImage(TILES_TEXTURE_FILE).getFWidth(),
						ArtLoader.getImage(TILES_TEXTURE_FILE).getFHeight()),
				LevelMap.TILE_WIDTH, TILE_WIDTH);

		/*
		 * ImageProcessor.irFore = new
		 * FloorRenderer(generator.getForegroundTiles(), new
		 * Vector2(ImageProcessor.getImage("Walls").getFWidth(),
		 * ImageProcessor.getImage("Walls").getFHeight()),
		 * LevelMap.getTileWidth(), LevelMap.getTileHeight());
		 */

		BaseRenderSystem.irBack.init();
		BaseRenderSystem.irWall.init();
		// ImageProcessor.irFore.init();

		Logger.info("Map Loaded");
	}

	public void renderHighTiles() {
		// ImageProcessor.irFore.draw(ImageProcessor.getImage("Walls").getID());
	}

	public void renderLowTiles() {
		BaseRenderSystem.irBack.draw(ArtLoader.getImage(TILES_TEXTURE_FILE).getID());
		BaseRenderSystem.irWall.draw(ArtLoader.getImage(TILES_TEXTURE_FILE).getID());
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
		Vector2f transportTexture = new Vector2f(1.0f, 7.0f);

		if (mazeTile.getAdjacentTile(Direction.N)) {
			insertTransporter(mapWidthMiddle - 2, 0, transportTexture, Direction.N);
			insertTransporter(mapWidthMiddle - 1, 0, transportTexture, Direction.N);
			insertTransporter(mapWidthMiddle, 0, transportTexture, Direction.N);
			insertTransporter(mapWidthMiddle + 1, 0, transportTexture, Direction.N);
		} else {
			insertWall(mapWidthMiddle - 2, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 1, 0, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 2, 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 1, 1, new Vector2f(1.0f, 2.0f));
		}
		if (mazeTile.getAdjacentTile(Direction.S)) {
			insertTransporter(mapWidthMiddle - 2, tileMap.getHeight() - 1, transportTexture, Direction.S);
			insertTransporter(mapWidthMiddle - 1, tileMap.getHeight() - 1, transportTexture, Direction.S);
			insertTransporter(mapWidthMiddle, tileMap.getHeight() - 1, transportTexture, Direction.S);
			insertTransporter(mapWidthMiddle + 1, tileMap.getHeight() - 1, transportTexture, Direction.S);
		} else {
			insertWall(mapWidthMiddle - 2, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 1, tileMap.getHeight() - 1, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 2, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle - 1, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
			insertWall(mapWidthMiddle + 1, tileMap.getHeight() - 2, new Vector2f(1.0f, 2.0f));
		}
		if (mazeTile.getAdjacentTile(Direction.W)) {
			insertTransporter(0, mapHeightMiddle - 2, transportTexture, Direction.W);
			insertTransporter(0, mapHeightMiddle - 1, transportTexture, Direction.W);
			insertTransporter(0, mapHeightMiddle, transportTexture, Direction.W);
			insertTransporter(0, mapHeightMiddle + 1, transportTexture, Direction.W);
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
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle - 2, transportTexture, Direction.E);
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle - 1, transportTexture, Direction.E);
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle, transportTexture, Direction.E);
			insertTransporter(tileMap.getHeight() - 1, mapHeightMiddle + 1, transportTexture, Direction.E);
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
		wallG.setMapPos(new Vector2f(texture));
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

	public float getRealWidth() {
		return tileMap.getWidth() * TILE_WIDTH;
	}

	public float getRealHeight() {
		return tileMap.getHeight() * TILE_WIDTH;
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
