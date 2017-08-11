package map;

import java.awt.image.BufferedImage;
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

	private static final String INPUT_LOCATION = "/Levels/Level5.png";
	private static final String OUTPUT_LOCATION = "../ShootEmUp/res/Levels/Level5.map";

	private static Map<Integer, MapTile> colours;

	private BufferedImage mapImage;
	private int size;
	private Vector2f[][] backgroundTiles;
	private Set<MapTile> walls;

	public TileGenerator() {
		loadMap(INPUT_LOCATION);

		this.size = mapImage.getWidth();
		backgroundTiles = new Vector2f[size][size];
		walls = new HashSet<>();
	}

	public static void main(String[] args) {
		initTileColours();

		TileGenerator generator = new TileGenerator();
		generator.setTiles();

		generator.saveTileMap();

	}

	private static void initTileColours() {
		colours = new HashMap<>();
		colours.put(-7864299, new MapTile(new Vector2f(1.0f, 2.0f), TileType.WALL)); // BrownWall
		colours.put(-8421505, new MapTile(new Vector2f(1.0f, 2.0f), TileType.WALL)); // GreyWall
		colours.put(-16735512, new MapTile(new Vector2f(1.0f, 1.0f), TileType.WATER)); // LightWater
		colours.put(-12629812, new MapTile(new Vector2f(1.0f, 1.0f), TileType.WATER)); // DarkWater
		colours.put(-4856291, new MapTile(new Vector2f(0.0f, 7.0f), TileType.GROUND)); // Grass
		colours.put(-1055568, new MapTile(new Vector2f(1.0f, 7.0f), TileType.GROUND)); // Path
		colours.put(-6075996, new MapTile(null, TileType.TRANSPORT)); // Path
	}

	private void loadMap(String fileLocation) {
		try {
			mapImage = ImageIO.read(getClass().getResource(fileLocation));
		} catch (IOException e) {
			Logger.error(e);
		}
	}

	public void setTiles() {
		Logger.info("Setting Tiles...");

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (colours.get(mapImage.getRGB(x, y)) == null) {
					Logger.warn("Tile colour not standard: " + mapImage.getRGB(x, y));
				}
				MapTile tile = new MapTile(colours.get(mapImage.getRGB(x, y)));
				if (tile.getTexture() != null) {
					if (tile.getType() == TileType.GROUND || tile.getType() == TileType.TRANSPORT) {
						backgroundTiles[x][y] = tile.getTexture();
					} else {
						tile.setPosition(new Vector2i(x, y));
						walls.add(tile);
					}
				}
			}
		}

		Logger.info("Tiles Set!");
	}

	public void saveTileMap() {
		Logger.info("Saving...");

		TileMap tileMap = new TileMap(size, size, backgroundTiles, walls);

		try (FileOutputStream fileOut = new FileOutputStream(OUTPUT_LOCATION)) {
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(tileMap);
			out.close();
			fileOut.close();
			Logger.info("Serialized data is saved in " + OUTPUT_LOCATION);
		} catch (IOException i) {
			Logger.error(i);
		}

		Logger.info("Save Complete!");
	}
}
