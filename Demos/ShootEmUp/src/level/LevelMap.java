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
import display.FloorRenderer;
import display.ImageProcessor;
import display.WallsRenderer;
import entity.Entity;
import logging.Logger;
import map.MapTile;
import map.TileMap;
import maze.Direction;
import maze.MazeTile;

public class LevelMap {

	public static final int TILE_WIDTH = 32;

	private final TileMap tileMap;
	private final GoalBounder goalBounder;
	private final String levelLayoutFileName;
	private static final String TILES_TEXTURE_FILE = "Tiles";
	private static final Vector2f WATER_MODIFIER = new Vector2f(0.0f, 4.0f);
	private MazeTile mazeTile;

	// collidable wall entities
	private final Map<Vector2i, Entity> walls = new HashMap<>();
	
	public LevelMap(MazeTile mazeTile) {
		Random rand = new Random();
		int levelLayoutFileId = rand.nextInt(5) + 1;
		
		this.mazeTile = mazeTile;
		levelLayoutFileName = ImageProcessor.LEVEL_FILE_LOCATION + levelLayoutFileId;
		tileMap = TileMap.readTileMap(levelLayoutFileName + ".map");
		goalBounder = GoalBounder.readGoalbounder(levelLayoutFileName + ".bound");
	}

	public LevelMap(MazeTile mazeTile, String levelLayoutFileName) {
		this.mazeTile = mazeTile;
		this.levelLayoutFileName = levelLayoutFileName;
		tileMap = TileMap.readTileMap(levelLayoutFileName + ".map");
		goalBounder = GoalBounder.readGoalbounder(levelLayoutFileName + ".bound");
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

		ImageProcessor.irBack.init();
		ImageProcessor.irWall.init();

		Logger.info("Map Loaded");
	}

	public void renderHighTiles() {
		//TODO: Add logic for rendering foreground tiles
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
		Vector2f transportTexture = new Vector2f(1.0f, 7.0f);

		if (mazeTile.getAdjacentTile(Direction.N)) {
			insertTransporterEntity(mapWidthMiddle - 2, 0, transportTexture, Direction.N);
			insertTransporterEntity(mapWidthMiddle - 1, 0, transportTexture, Direction.N);
			insertTransporterEntity(mapWidthMiddle, 0, transportTexture, Direction.N);
			insertTransporterEntity(mapWidthMiddle + 1, 0, transportTexture, Direction.N);
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
			insertTransporterEntity(mapWidthMiddle - 2, tileMap.getHeight() - 1, transportTexture, Direction.S);
			insertTransporterEntity(mapWidthMiddle - 1, tileMap.getHeight() - 1, transportTexture, Direction.S);
			insertTransporterEntity(mapWidthMiddle, tileMap.getHeight() - 1, transportTexture, Direction.S);
			insertTransporterEntity(mapWidthMiddle + 1, tileMap.getHeight() - 1, transportTexture, Direction.S);
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
			insertTransporterEntity(0, mapHeightMiddle - 2, transportTexture, Direction.W);
			insertTransporterEntity(0, mapHeightMiddle - 1, transportTexture, Direction.W);
			insertTransporterEntity(0, mapHeightMiddle, transportTexture, Direction.W);
			insertTransporterEntity(0, mapHeightMiddle + 1, transportTexture, Direction.W);
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
			insertTransporterEntity(tileMap.getHeight() - 1, mapHeightMiddle - 2, transportTexture, Direction.E);
			insertTransporterEntity(tileMap.getHeight() - 1, mapHeightMiddle - 1, transportTexture, Direction.E);
			insertTransporterEntity(tileMap.getHeight() - 1, mapHeightMiddle, transportTexture, Direction.E);
			insertTransporterEntity(tileMap.getHeight() - 1, mapHeightMiddle + 1, transportTexture, Direction.E);
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
		createWallEntity(wallG, x, y);
	}

	// create water
	private void insertWater(int x, int y, Vector2f texture) {
		MapGraphics wallG = new MapGraphics(TILES_TEXTURE_FILE);
		wallG.setMapPos(texture.add(WATER_MODIFIER));
		createWallEntity(wallG, x, y);
	}

	// create wall
	private void insertTransporterEntity(int x, int y, Vector2f texture, Direction direction) {
		Entity transporter = new Entity();
		MapGraphics transporterG = new MapGraphics(TILES_TEXTURE_FILE);
		transporterG.setMapPos(new Vector2f(texture));
		transporterG.setX(x * TILE_WIDTH);
		transporterG.setY(y * TILE_WIDTH);
		transporter.addComponent(transporterG);
		BaseCollision collision = new TransportCollision(direction);
		transporter.addComponent(collision);
		walls.put(new Vector2i(x, y), transporter);
	}

	private void createWallEntity(MapGraphics wallG, int x, int y) {
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
	
	public String getlevelLayoutFileName() {
		return levelLayoutFileName;
	}
}
