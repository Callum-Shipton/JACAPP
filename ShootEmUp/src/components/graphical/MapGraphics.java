package components.graphical;

import org.joml.Vector2f;

import entity.Entity;

public class MapGraphics extends BaseGraphics {

	private transient Vector2f mapPos;

	public MapGraphics(String imageId) {
		super(imageId, null);
	}

	public MapGraphics(MapGraphics mapGraphics) {
		this(mapGraphics.imageId);
	}

	public Vector2f getMapPos() {
		return mapPos;
	}

	@Override
	public void render(Entity e) {

	}

	public void setMapPos(Vector2f mapPos) {
		this.mapPos = mapPos;
	}

	@Override
	public void update() {

	}

}
