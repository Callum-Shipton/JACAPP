package map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.joml.Vector2f;
import org.joml.Vector2i;

import logging.Logger;
import map.MapTile.TileType;

public class TileGenerator {

	private static final String MAP_FOLDER_PATH = "C:/Users/Jack/repos/JACAPP/Demos/ShootEmUp/res/Levels/";
	private static final int NUMBER_OF_LEVELS = 4;

	private static Map<Integer, TileType> colours;

	private BufferedImage mapImage;
	private int size;
	private Vector2f[][] backgroundTiles;
	private Set<MapTile> walls;
	private String mapPngFileName;
	
	private static final Vector2f WALL_BOTTOM_LEFT = new Vector2f(0, 3);
	private static final Vector2f WALL_BOTTOM = new Vector2f(1, 3);
	private static final Vector2f WALL_BOTTOM_RIGHT = new Vector2f(2, 3);
	private static final Vector2f WALL_LEFT = new Vector2f(0, 2);
	private static final Vector2f WALL_MIDDLE = new Vector2f(1, 2);
	private static final Vector2f WALL_RIGHT = new Vector2f(2, 2);
	private static final Vector2f CEILING_LEFT = new Vector2f(0, 1);
	private static final Vector2f CEILING_MIDDLE = new Vector2f(1, 1);
	private static final Vector2f CEILING_RIGHT = new Vector2f(2, 1);
	private static final Vector2f CEILING_LEFT_CORNER = new Vector2f(0, 0);
	private static final Vector2f CEILING_RIGHT_CORNER = new Vector2f(2, 0);
	

	private static final Vector2f GRASS_TEXTURE = new Vector2f(0, 7);
	private static final Vector2f PATH_TEXTURE = new Vector2f(1, 7);

	public TileGenerator(String mapPngFileName) {
		this.mapPngFileName = mapPngFileName;
		loadMap(mapPngFileName + ".png");

		this.size = mapImage.getWidth();
		backgroundTiles = new Vector2f[size][size];
		walls = new HashSet<>();
	}

	public static void main(String[] args) {
		initTileColours();
		
		for(int i = 1; i <= NUMBER_OF_LEVELS; i++) {
			TileGenerator generator = new TileGenerator(MAP_FOLDER_PATH + "Level" + i);
			generator.setTiles();
			generator.saveTileMap();
		}
	}

	private static void initTileColours() {
		colours = new HashMap<>();
		colours.put(-7864299, TileType.WALL); // BrownWall
		colours.put(-8421505, TileType.WALL); // GreyWall
		colours.put(-16735512, TileType.WATER); // LightWater
		colours.put(-12629812, TileType.WATER); // DarkWater
		colours.put(-4856291, TileType.GROUND); // Grass
		colours.put(-1055568, TileType.GROUND); // Path
		colours.put(-6075996, TileType.TRANSPORT); // Transport Tile
	}

	private void loadMap(String fileLocation) {
		try {
			mapImage = ImageIO.read(new File(fileLocation));
		} catch (IOException e) {
			Logger.error(e);
		}
	}
	
	private TileType getTileType(int x, int y) {
		int tileRgb = mapImage.getRGB(x, y);
		TileType tileType = colours.get(tileRgb);
		if (tileType == null) {
			Logger.warn("Tile colour not standard: " + mapImage.getRGB(x, y));
		}
		return tileType;
	}
	
	public TileType[][] getTileTypes() {
		
		TileType[][] tileTypes = new TileType[size][size];
		
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				tileTypes[x][y] = getTileType(x, y);
			}
		}
		
		return tileTypes;
	}
	
	public void setBackgroundTiles(TileType[][] tileTypes) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				switch(tileTypes[x][y]) {
					case GROUND:
					case TRANSPORT:
						backgroundTiles[x][y] = GRASS_TEXTURE;
					default:
				}
			}
		}
		
	}
	
	public void setWallTiles(TileType[][] tileTypes) {
		
		boolean previousXIsWall = false;
		Vector2f texture;
		
		for (int y = size - 1; y >= 0; y--) {
			previousXIsWall = false;
			for (int x = 0; x < size; x++) {
				if(tileTypes[x][y] == TileType.WALL) {
					
					if(y + 1  < size && tileTypes[x][y+1] == TileType.WALL) {
						if (previousXIsWall) {
							if(x + 1 < size && tileTypes[x+1][y] != TileType.WALL) {
								texture = WALL_RIGHT;
							} else {
								texture = WALL_MIDDLE;
							}
						} else {
							texture = WALL_LEFT;
						}
					} else {
						if (previousXIsWall) {
							if(x + 1 < size && tileTypes[x+1][y] != TileType.WALL) {
								texture = WALL_BOTTOM_RIGHT;
							} else {
								texture = WALL_BOTTOM;
							}
						} else {
							texture = WALL_BOTTOM_LEFT;
						}
					}
					
					MapTile tile = new MapTile(new Vector2i(x, y), texture, TileType.WALL);
					walls.add(tile);
					
					previousXIsWall = true;
				} else {
					previousXIsWall = false;
				}
			}
		}
	}
	
	public void setWaterTiles(TileType[][] tileTypes) {
		
		boolean previousXIsWall = false;
		Vector2f texture;
		
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if(tileTypes[x][y] == TileType.WATER) {
					if (previousXIsWall) {
						if(x + 1 < size && tileTypes[x+1][y] != TileType.WATER) {
							texture = WALL_BOTTOM_RIGHT;
						} else {
							texture = WALL_BOTTOM;
						}
					} else {
						texture = WALL_BOTTOM_LEFT;
					}
					
					MapTile tile = new MapTile(new Vector2i(x, y), texture, TileType.WATER);
					walls.add(tile);
					
					previousXIsWall = true;
				} else {
					previousXIsWall = false;
				}
			}
		}
	}

	public void setTiles() {
		Logger.info("Setting Tiles...");
		
		TileType[][] tileTypes = getTileTypes();
		setBackgroundTiles(tileTypes);
		setWallTiles(tileTypes);
		setWaterTiles(tileTypes);

		Logger.info("Tiles Set!");
	}

	public void saveTileMap() {
		Logger.info("Saving...");

		TileMap tileMap = new TileMap(size, size, backgroundTiles, walls);
		
		String outputFilePath = mapPngFileName + ".map";

		try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(tileMap);
			out.close();
			fileOut.close();
			Logger.info("Serialized data is saved in " + outputFilePath);
		} catch (IOException i) {
			Logger.error(i);
		}

		Logger.info("Save Complete!");
	}
}
