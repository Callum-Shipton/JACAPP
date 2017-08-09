package map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.joml.Vector2f;

import logging.Logger;

public class TileMap implements Serializable {

	private static final long serialVersionUID = 2960526026451120160L;

	private int width;
	private int height;

	private Vector2f[][] backgroundTiles;
	private Vector2f[][] walls;

	public TileMap(int width, int height, Vector2f[][] backgroundTiles, Vector2f[][] walls) {
		this.width = width;
		this.height = height;
		this.backgroundTiles = backgroundTiles;
		this.walls = walls;

	}

	public static TileMap readTileMap(String location) {
		TileMap tileMap = null;
		try (ObjectInputStream in = new ObjectInputStream(TileMap.class.getResourceAsStream(location))) {
			tileMap = (TileMap) in.readObject();
			in.close();
		} catch (IOException i) {
			Logger.error(i);
		} catch (ClassNotFoundException c) {
			Logger.error("Employee class not found");
			Logger.error(c);
		}

		return tileMap;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Vector2f[][] getWalls() {
		return walls;
	}

	public Vector2f[][] getBackgroundTiles() {
		return backgroundTiles;
	}
}
