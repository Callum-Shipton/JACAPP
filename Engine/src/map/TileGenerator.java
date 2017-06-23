package map;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import logging.Logger;
import math.Vector2;

public class TileGenerator {

	private BufferedImage mapImage = null;

	private Vector2[][] backgroundTiles;
	private Vector2[][] walls;
	private Vector2[][] foregroundTiles;

	private int widthInTiles;
	private int heightInTiles;

	public TileGenerator(String fileLocation) {
		loadMap(fileLocation);
	}

	private void loadMap(String fileLocation) {
		try {
			mapImage = ImageIO.read(getClass().getResource(fileLocation));
		} catch (IOException e) {
			Logger.error(e);
		}
	}

	public void generateTiles() {

	}

	public Vector2[][] getBackgroundTiles() {
		return backgroundTiles;
	}

	public Vector2[][] getWalls() {
		return walls;
	}

	public Vector2[][] getForegroundTiles() {
		return foregroundTiles;
	}

	public int getWidthInTiles() {
		return widthInTiles;
	}

	public int getHeightInTiles() {
		return heightInTiles;
	}

}
