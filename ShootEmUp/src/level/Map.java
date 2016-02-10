package level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import object.Entity;
import main.ShootEmUp;
import math.Vector2;
import components.collision.RigidCollision;
import components.graphical.MapGraphics;
import display.Art;
import display.IRenderer;

public class Map {

	private final int WALL = -7864299;
	private final int WATER = -16735512;
	private final int GRASS = -4856291;
	private final int PATH = -1055568;
	
	private int width;
	private int height;
	private String file;
	private Vector2[][] backgroundTiles; //Replace with Irenderer changes
	private Vector2[][] wallTiles; // ^^
	private Vector2[][] foregroundTiles; // ^^
	private BufferedImage map = null;
	public HashMap<Vector2, Entity> walls;

	public Map(String file){
		
		this.file = file;
		loadMap();
		width = (map.getWidth());
		height = map.getHeight();
		backgroundTiles = new Vector2[map.getWidth()][map.getHeight()];
		wallTiles = new Vector2[map.getWidth()][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth()][map.getHeight()];
		walls = new HashMap<Vector2,Entity>();
	}
	
	private void loadMap() {
		try {
			map = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
		}
	}
	
	public void setTiles() {
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (map.getRGB(x, y)) {
				case GRASS:
					backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case PATH:
					backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				case WALL:
					System.out.println(x + " " + y);
					insertWall(x, y);
					break;
				case WATER:
					System.out.println(x + " " + y);
					insertWater(x, y);
					break;
				default:
					System.out.println(map.getRGB(x, y));
				}
			}
		}
		Art.irBack = new IRenderer(backgroundTiles, new Vector2(Art.floor.getFWidth(), Art.floor.getFHeight()), Art.floor.getWidth() / Art.floor.getFWidth(), Art.floor.getHeight() / Art.floor.getFHeight());
		Art.irWall = new IRenderer(walls, new Vector2(Art.wall.getFWidth(), Art.wall.getFHeight()), Art.wall.getWidth() / Art.wall.getFWidth(), Art.wall.getHeight() / Art.wall.getFHeight());
		Art.irFore = new IRenderer(foregroundTiles, new Vector2(Art.wall.getFWidth(), Art.wall.getFHeight()), Art.wall.getWidth() / Art.wall.getFWidth(), Art.wall.getHeight() / Art.wall.getFHeight());
	}
	
	public void insertWall(int x, int y){
		float width = Art.wall.getWidth()/Art.wall.getFWidth();
		float height = Art.wall.getHeight()/Art.wall.getFHeight();
		Entity wall = new Entity();
		MapGraphics wallG = new MapGraphics(Art.wall, new Vector2(1.0f, 2.0f), x * width, y * height);
		wall.addComponent(wallG);
		RigidCollision MC = new RigidCollision(wall);
		wall.addComponent(MC);
		ShootEmUp.currentLevel.entities.add(wall);
		walls.put(new Vector2(x,y), wall);
	}
	
	public void insertWater(int x, int y){
		float width = Art.wall.getWidth()/Art.wall.getFWidth();
		float height = Art.wall.getHeight()/Art.wall.getFHeight();
		Entity wall = new Entity();
		MapGraphics wallG = new MapGraphics(Art.wall, new Vector2(4.0f, 2.0f), x * width, y * height);
		wall.addComponent(wallG);
		RigidCollision MC = new RigidCollision(wall);
		wall.addComponent(MC);
		ShootEmUp.currentLevel.entities.add(wall);
		walls.put(new Vector2(x,y), wall);
	}
	
	public void renderLowTiles() {
		Art.irBack.draw(Art.floor.getID());
		Art.irWall.draw(Art.wall.getID());
	}

	public void renderHighTiles() {
		Art.irFore.draw(Art.wall.getID());
	}
	
	//Getters 
	
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
