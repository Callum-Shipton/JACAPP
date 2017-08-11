package map;

import java.io.Serializable;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class MapTile implements Serializable {

	private static final long serialVersionUID = 7981995137822225178L;

	private Vector2i position;
	private Vector2f texture;
	private TileType type;

	public MapTile(Vector2i position, Vector2f texture, TileType type) {
		this.position = position;
		this.texture = texture;
		this.type = type;
	}

	public MapTile(Vector2f texture, TileType type) {
		this(null, texture, type);
	}

	public MapTile(MapTile mapTile) {
		position = mapTile.position;
		texture = mapTile.texture;
		type = mapTile.type;
	}

	public void setPosition(Vector2i position) {
		this.position = position;
	}

	public Vector2i getPosition() {
		return position;
	}

	public Vector2f getTexture() {
		return texture;
	}

	public TileType getType() {
		return type;
	}

	public enum TileType implements Serializable {
		WALL, WATER, GROUND, TRANSPORT
	}

}
