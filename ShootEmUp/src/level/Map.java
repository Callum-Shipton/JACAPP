package level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import main.ShootEmUp;
import math.Vector2;
import components.collision.RigidCollision;
import components.graphical.MapGraphics;
import display.Art;
import display.IRenderer;
import entities.Entity;

public class Map {

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
		width = (map.getWidth() / 3);
		height = map.getHeight();
		backgroundTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		wallTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		foregroundTiles = new Vector2[map.getWidth() / 3][map.getHeight()];
		walls = new HashMap<Vector2,Entity>();
	}
	
	private void loadMap() {
		try {
			map = ImageIO.read(getClass().getResource(file));
		} catch (IOException e) {
		}
	}
	
	public void setTiles() {

		boolean noWall;
		float width = Art.wall.getWidth()/Art.wall.getFWidth();
		float height = Art.wall.getHeight()/Art.wall.getFHeight();
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth() / 3; x++) {
				switch (map.getRGB(x, y)) {
				case -1:
					backgroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				case -16777216:
					backgroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				default:
					System.out.println(map.getRGB(x, y));
				}
				
				//creating walls
				noWall = false;
				Entity wall = new Entity();
				MapGraphics wallG = null;
				switch (map.getRGB(x + (map.getWidth() / 3), y)) {
				case -1:
					noWall = true;
					break;
				case -3584:
					wallG = new MapGraphics(Art.wall, new Vector2(5.0f, 0.0f), x * width, y * height);
					break;
				case -14066:
					wallG = new MapGraphics(Art.wall, new Vector2(7.0f, 1.0f), x * width, y * height);
					break;
				case -20791:
					wallG = new MapGraphics(Art.wall, new Vector2(0.0f, 2.0f), x * width, y * height);
					break;
				case -32985:
					wallG = new MapGraphics(Art.wall, new Vector2(4.0f, 0.0f), x * width, y * height);
					break;
				case -1055568:
					wallG = new MapGraphics(Art.wall, new Vector2(6.0f, 1.0f), x * width, y * height);
					break;
				case -1237980:
					wallG = new MapGraphics(Art.wall, new Vector2(3.0f, 0.0f), x * width, y * height);
					break;
				case -3620889:
					wallG = new MapGraphics(Art.wall, new Vector2(2.0f, 1.0f), x * width, y * height);
					break;
				case -3947581:
					wallG = new MapGraphics(Art.wall, new Vector2(2.0f, 2.0f), x * width, y * height);
					break;
				case -4621737:
					wallG = new MapGraphics(Art.wall, new Vector2(1.0f, 2.0f), x * width, y * height);
					break;
				case -4856291:
					wallG = new MapGraphics(Art.wall, new Vector2(5.0f, 1.0f), x * width, y * height);
					break;
				case -6075996:
					wallG = new MapGraphics(Art.wall, new Vector2(1.0f, 1.0f), x * width, y * height);
					break;
				case -6694422:
					wallG = new MapGraphics(Art.wall, new Vector2(4.0f, 1.0f), x * width, y * height);
					break;
				case -7864299:
					wallG = new MapGraphics(Art.wall, new Vector2(2.0f, 0.0f), x * width, y * height);
					break;
				case -8355840:
					wallG = new MapGraphics(Art.wall, new Vector2(3.0f, 2.0f), x * width, y * height);
					break;
				case -8421505:
					wallG = new MapGraphics(Art.wall, new Vector2(1.0f, 0.0f), x * width, y * height);
					break;
				case -9399618:
					wallG = new MapGraphics(Art.wall, new Vector2(3.0f, 1.0f), x * width, y * height);
					break;
				case -14503604:
					wallG = new MapGraphics(Art.wall, new Vector2(6.0f, 0.0f), x * width, y * height);
					break;
				case -12629812:
					wallG = new MapGraphics(Art.wall, new Vector2(0.0f, 1.0f), x * width, y * height);
					break;
				case -16735512:
					wallG = new MapGraphics(Art.wall, new Vector2(7.0f, 0.0f), x * width, y * height);
					break;
				case -16777216:
					wallG = new MapGraphics(Art.wall, new Vector2(0.0f, 0.0f), x * width, y * height);
					break;
				default:
					System.out.println(map.getRGB(x + (map.getWidth() / 3), y));
				}
				
				if(!noWall){
					wall.addComponent(wallG);
					RigidCollision MC = new RigidCollision(wall);
					wall.addComponent(MC);
					ShootEmUp.currentLevel.entities.add(wall);
					walls.put(new Vector2(x,y), wall);
				}
				
				switch (map.getRGB(x + ((map.getWidth() / 3) * 2), y)) {
				case -1:
					break;
				case -7864299:
					foregroundTiles[x][y] = new Vector2(2.0f, 0.0f);
					break;
				case -8421505:
					foregroundTiles[x][y] = new Vector2(1.0f, 0.0f);
					break;
				case -16777216:
					foregroundTiles[x][y] = new Vector2(0.0f, 0.0f);
					break;
				default:
					System.out.println(map.getRGB(x + ((map.getWidth() / 3) * 2), y));
				}
			}
		}
		Art.irBack = new IRenderer(backgroundTiles, new Vector2(4.0f, 4.0f), 32.0f, 32.0f);
		Art.irWall = new IRenderer(walls, new Vector2(8.0f, 8.0f), 32.0f, 32.0f);
		Art.irFore = new IRenderer(foregroundTiles, new Vector2(4.0f, 4.0f), 32.0f, 32.0f);
	}
	
	public void renderLowTiles() {
		Art.irBack.draw(Art.background.getID());
		Art.irWall.draw(Art.wall.getID());
	}

	public void renderHighTiles() {
		Art.irFore.draw(Art.foreground.getID());
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
