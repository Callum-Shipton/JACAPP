package map;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.joml.Vector2f;

import logging.Logger;

public class TileGenerator {

	private static final String INPUT_LOCATION = "/Levels/Level2.png";
	private static final String OUTPUT_LOCATION = "../ShootEmUp/res/Levels/Level2.map";

	private static Map<Integer, Vector2f> colours;

	private BufferedImage mapImage;
	private int size;
	private Vector2f[][] backgroundTiles;
	private Vector2f[][] walls;

	public TileGenerator() {
		loadMap(INPUT_LOCATION);

		this.size = mapImage.getWidth();
		backgroundTiles = new Vector2f[size][size];
		walls = new Vector2f[size][size];
	}

	public static void main(String[] args) {
		initTileColours();

		TileGenerator generator = new TileGenerator();
		generator.setTiles();

		generator.saveTileMap();

	}

	private static void initTileColours() {
		colours = new HashMap<>();
		colours.put(-7864299, new Vector2f(1.0f, 2.0f)); // Brown Wall
		colours.put(-8421505, new Vector2f(1.0f, 2.0f)); // Grey/ Wall
		colours.put(-16735512, new Vector2f(1.0f, 1.0f)); // Light Water
		colours.put(-12629812, new Vector2f(1.0f, 1.0f)); // Dark Water
		colours.put(-4856291, new Vector2f(0.0f, 7.0f)); // Grass
		colours.put(-1055568, new Vector2f(1.0f, 7.0f)); // Path
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

		// Set Background Tiles
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				backgroundTiles[x][y] = colours.get(mapImage.getRGB(x, y));
			}
		}

		// insert correct wall/water type and side
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				walls[x][y] = colours.get(mapImage.getRGB(x, y));
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
