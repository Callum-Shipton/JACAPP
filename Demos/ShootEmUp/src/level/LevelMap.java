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
import display.Image;
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
	
	private final Vector2f transportTexture = new Vector2f(1.0f, 7.0f);
	private final Vector2f wallTexture = new Vector2f(1.0f, 2.0f);
	
	private static final Random rand = new Random();
	
	private static final int NUMBER_OF_LEVELS = 4;
	
	public LevelMap(MazeTile mazeTile) {
		this(mazeTile, ImageProcessor.LEVEL_FILE_LOCATION + (rand.nextInt(NUMBER_OF_LEVELS) + 1));
	}

	public LevelMap(MazeTile mazeTile, String levelLayoutFileName) {
		this.levelLayoutFileName = levelLayoutFileName;
		
		this.mazeTile = mazeTile;
		tileMap = TileMap.readTileMap(levelLayoutFileName + ".map");
		goalBounder = GoalBounder.readGoalbounder(levelLayoutFileName + ".bound");
	}

	public void init() {

		Image textureMap = ImageProcessor.getImage(TILES_TEXTURE_FILE);
		createWalls(tileMap.getWalls());
		
		ImageProcessor.irBack = new FloorRenderer(tileMap.getBackgroundTiles(), new Vector2f(textureMap.getFWidth(), textureMap.getFHeight()), LevelMap.TILE_WIDTH, TILE_WIDTH);
		ImageProcessor.irWall = new WallsRenderer(walls, new Vector2f(textureMap.getFWidth(), textureMap.getFHeight()), LevelMap.TILE_WIDTH, TILE_WIDTH);

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
			
			Vector2i wallPosition = wall.getPosition();
			
			switch (wall.getType()) {
			case WALL:
				insertWall(wallPosition.x, wallPosition.y, wall.getTexture(), false);
				break;
			case WATER:
				insertWall(wallPosition.x, wallPosition.y, wall.getTexture(), true);
				break;
			default:
			}
		}

		int mapWidthMiddle = tileMap.getWidth() / 2;
		int mapHeightMiddle = tileMap.getHeight() / 2;
		
		fillMapHole(Direction.N, mapWidthMiddle, 0);
		fillMapHole(Direction.S, mapWidthMiddle, tileMap.getHeight() - 1);
		fillMapHole(Direction.W, 0, mapHeightMiddle);
		fillMapHole(Direction.E, tileMap.getHeight() - 1, mapHeightMiddle);
	}
	
	private void fillMapHole(Direction direction, int x, int y) {
		if (mazeTile.hasAdjacentTile(direction)) {
			insertTransporterEntityBlock(x, y, direction);
		} else {
			insertWallEntityBlock(x, y, direction);
		}
	}
	
	private void insertWallEntityBlock(int x, int y, Direction direction) {
		int newX = x;
		int newY = y;
		
		for(int modifier = -2; modifier <= 1; modifier++) {
			for(int modifier2 = 0; modifier2 <= 1; modifier2++) {
				newX = x;
				newY = y;
				
				switch(direction) {
				case N:
					newX += modifier;
					newY += modifier2;
					break;
				case S:
					newX += modifier;
					newY += modifier2 - 1;
					break;
				case W:
					newY += modifier;
					newX += modifier2;
					break;
				case E:
					newY += modifier;
					newX += modifier2 - 1;
				}
				
				insertWall(newX, newY, wallTexture, false);
			}
		}
	}
	
	private void insertTransporterEntityBlock(int x, int y, Direction direction) {
		int newX;
		int newY;
		
		for(int modifier = -2; modifier <= 1; modifier++) {
			newX = x;
			newY = y;
			
			if(direction == Direction.N || direction == Direction.S) {
				newX += modifier;
			} else {
				newY += modifier;
			}
			
			insertTransporterEntity(newX, newY, direction);
		}
	}

	private void insertWall(int x, int y, Vector2f texture, boolean isWater) {
		MapGraphics wallG = new MapGraphics(TILES_TEXTURE_FILE);
		
		Vector2f wallTexture = new Vector2f(texture);
		if(isWater) {
			texture.add(WATER_MODIFIER);
		}
		wallG.setMapPos(wallTexture);
		wallG.setX(x * TILE_WIDTH);
		wallG.setY(y * TILE_WIDTH);
		
		RigidCollision rigidCollision = new RigidCollision();

		Entity wall = new Entity();
		
		wall.addComponent(wallG);
		wall.addComponent(rigidCollision);
		
		walls.put(new Vector2i(x, y), wall);
	}

	private void insertTransporterEntity(int x, int y, Direction direction) {
		Entity transporter = new Entity();
		
		MapGraphics transporterG = new MapGraphics(TILES_TEXTURE_FILE);
		transporterG.setMapPos(new Vector2f(transportTexture));
		transporterG.setX(x * TILE_WIDTH);
		transporterG.setY(y * TILE_WIDTH);

		BaseCollision collision = new TransportCollision(direction);
		
		transporter.addComponent(transporterG);
		transporter.addComponent(collision);
		
		walls.put(new Vector2i(x, y), transporter);
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
