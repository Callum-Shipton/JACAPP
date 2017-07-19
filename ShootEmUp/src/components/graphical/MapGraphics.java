package components.graphical;

import org.joml.Vector2f;

import display.Image;
import entity.Entity;

public class MapGraphics extends BaseGraphics {

	private Vector2f mapPos;

	public MapGraphics(Image image, Vector2f mapPos, float x, float y, Entity entity) {
		super(entity);

		this.image = image;
		width = image.getWidth() / image.getFWidth();
		height = image.getHeight() / image.getFHeight();
		setMapPos(mapPos);
		this.x = x;
		this.y = y;
	}

	public Vector2f getMapPos() {
		return this.mapPos;
	}

	@Override
	public void render(Entity e) {
		// TODO Auto-generated method stub

	}

	public void setMapPos(Vector2f mapPos) {
		this.mapPos = mapPos;
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}

}
