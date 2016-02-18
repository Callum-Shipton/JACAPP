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

	//RGB integer values for tiles
	private final int WALL = -7864299;
	private final int WATER = -16735512;
	private final int GRASS = -4856291;
	private final int PATH = -1055568;
	
	//size of map
	private int width;
	private int height;
	
	//map layout image
	private String file;
	private BufferedImage map = null;
	
	//Tile type arrays
	private int[][] backgroundTileTypes; //Replace with Irenderer changes
	private int[][] wallTileTypes; // ^^
	private int[][] foregroundTileTypes;
	
	//texture map position arrays
	private Vector2[][] backgroundTiles; //Replace with Irenderer changes
	private Vector2[][] wallTiles; // ^^
	private Vector2[][] foregroundTiles; // ^^
	
	//collidable wall entities
	public HashMap<Vector2, Entity> walls;

	public Map(String file){
		
		this.file = file;
		loadMap();
		width = map.getWidth();
		height = map.getHeight();
		backgroundTileTypes = new int[map.getWidth()][map.getHeight()];
		wallTileTypes = new int[map.getWidth()][map.getHeight()];
		foregroundTileTypes = new int[map.getWidth()][map.getHeight()];
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
	
	public void setTileTypes() {
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (map.getRGB(x, y)) {
				case GRASS:
					backgroundTileTypes[x][y] = 1;
					break;
				case PATH: 
					backgroundTileTypes[x][y] = 2;
					break;
				case WALL:
					wallTileTypes[x][y] = 1;
					break;
				case WATER:
					wallTileTypes[x][y] = 9;
					break;
				default:
					System.out.println(map.getRGB(x, y));
				}
			}
		}
		//Set Wall & Water Types
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				//if a wall
				if(wallTileTypes[x][y] == 1){
					//if not the left edge of the map
					if(x > 0){
						//if a wall to the left
						if((wallTileTypes[x-1][y] == 1) || (wallTileTypes[x-1][y] == 2)){
							wallTileTypes[x][y] = 2;
						}
						
						if(((wallTileTypes[x-1][y] == 4) || (wallTileTypes[x-1][y] == 5))||(wallTileTypes[x-1][y] == 7)){
							wallTileTypes[x][y] = 5;
						}
						
						//if not the right edge
						if(x < map.getWidth()-1){
							//if the end horizontal wall
							if((wallTileTypes[x+1][y] == 0) || (wallTileTypes[x+1][y] > 8)){
								if((wallTileTypes[x-1][y] == 4) || (wallTileTypes[x-1][y] == 5)){
									wallTileTypes[x][y] = 6;
								} else {
									wallTileTypes[x][y] = 3;
								}
							}
						}
					}
					//if not the top edge of the map
					if(y < map.getHeight()-1){
						if((wallTileTypes[x][y+1] == 0) || (wallTileTypes[x][y+1] > 8)){
							if(x > 0){
								if((wallTileTypes[x-1][y] == 0) || (wallTileTypes[x-1][y] > 8)){
									wallTileTypes[x][y] = 4;
								}
							}
						}
					}
					if(((x > 0) && (y > 0)) && ((x < map.getWidth()-1) && (y < map.getHeight()-1))){
						if((wallTileTypes[x-1][y] <= 8) && (wallTileTypes[x-1][y] != 0)){
							if((wallTileTypes[x+1][y] <= 8) && (wallTileTypes[x+1][y] != 0)){
								if((wallTileTypes[x][y-1] <= 8) && (wallTileTypes[x][y-1] != 0)){
									if((wallTileTypes[x][y+1] <= 8) && (wallTileTypes[x][y+1] != 0)){
										if((wallTileTypes[x+1][y+1] == 0) || (wallTileTypes[x+1][y+1] > 8)){
											wallTileTypes[x][y] = 7;
										} else if((wallTileTypes[x-1][y+1] == 0) || (wallTileTypes[x-1][y+1] > 8)){
											wallTileTypes[x][y] = 8;
										}
									}
								}
							}
						}
					}
				}
				if(wallTileTypes[x][y] == 9){
					wallTileTypes[x][y] = 13;
				}
			}
		}
		
		setTiles();
		
		Art.irBack = new IRenderer(backgroundTiles, new Vector2(Art.floor.getFWidth(), Art.floor.getFHeight()), Art.floor.getWidth() / Art.floor.getFWidth(), Art.floor.getHeight() / Art.floor.getFHeight());
		Art.irWall = new IRenderer(walls, new Vector2(Art.wall.getFWidth(), Art.wall.getFHeight()), Art.wall.getWidth() / Art.wall.getFWidth(), Art.wall.getHeight() / Art.wall.getFHeight());
		Art.irFore = new IRenderer(foregroundTiles, new Vector2(Art.wall.getFWidth(), Art.wall.getFHeight()), Art.wall.getWidth() / Art.wall.getFWidth(), Art.wall.getHeight() / Art.wall.getFHeight());
	}
	
	public void setTiles(){
		//Set Background Tiles
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (backgroundTileTypes[x][y]) {
				case 1:
					backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case 2:
					backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				}
			}
		}
		//Set Wall Tiles
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (wallTileTypes[x][y]) {
				case 1:
					insertWall(x, y, 0.0f, 2.0f);
					break;
				case 2:
					insertWall(x, y, 1.0f, 2.0f);
					break;
				case 3:
					insertWall(x, y, 2.0f, 2.0f);
					break;
				case 4:
					insertWall(x, y, 0.0f, 3.0f);
					break;
				case 5:
					insertWall(x, y, 1.0f, 3.0f);
					break;
				case 6:
					insertWall(x, y, 2.0f, 3.0f);
					break;
				case 7:
					insertWall(x, y, 4.0f, 0.0f);
					break;
				case 8:
					insertWall(x, y, 5.0f, 0.0f);
					break;
				case 9:
					insertWater(x, y, 3.0f, 1.0f);
					break;
				case 10:
					insertWater(x, y, 4.0f, 1.0f);
					break;
				case 11:
					insertWater(x, y, 5.0f, 1.0f);
					break;
				case 12:
					insertWater(x, y, 3.0f, 2.0f);
					break;
				case 13:
					insertWater(x, y, 4.0f, 2.0f);
					break;
				case 14:
					insertWater(x, y, 5.0f, 2.0f);
					break;
				case 15:
					insertWater(x, y, 3.0f, 3.0f);
					break;
				case 16:
					insertWater(x, y, 4.0f, 3.0f);
					break;
				case 17:
					insertWater(x, y, 5.0f, 3.0f);
					break;
				}
			}
		}
		//Set Foreground Tiles
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				switch (foregroundTileTypes[x][y]) {
				
				}
			}
		}
	}
	
	public void insertWall(int x, int y, float tileMapX, float tileMapY){
		float width = Art.wall.getWidth()/Art.wall.getFWidth();
		float height = Art.wall.getHeight()/Art.wall.getFHeight();
		Entity wall = new Entity();
		MapGraphics wallG = new MapGraphics(Art.wall, new Vector2(tileMapX, tileMapY), x * width, y * height);
		wall.addComponent(wallG);
		RigidCollision MC = new RigidCollision(wall);
		wall.addComponent(MC);
		ShootEmUp.currentLevel.entities.add(wall);
		walls.put(new Vector2(x,y), wall);
	}
	
	public void insertWater(int x, int y, float tileMapX, float tileMapY){
		float width = Art.wall.getWidth()/Art.wall.getFWidth();
		float height = Art.wall.getHeight()/Art.wall.getFHeight();
		Entity wall = new Entity();
		MapGraphics wallG = new MapGraphics(Art.wall, new Vector2(tileMapX, tileMapY), x * width, y * height);
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
