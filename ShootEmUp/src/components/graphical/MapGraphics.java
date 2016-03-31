package components.graphical;

import components.Message;
import display.Image;
import math.Vector2;
import object.Entity;

public class MapGraphics extends BaseGraphics {

	private Vector2 mapPos;

	public MapGraphics(Image image, Vector2 mapPos, float x, float y) {
		this.image = image;
		width = image.getWidth() / image.getFWidth();
		height = image.getHeight() / image.getFHeight();
		setMapPos(mapPos);
		this.x = x;
		this.y = y;
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	public Vector2 getMapPos() {
		return mapPos;
	}

	public void setMapPos(Vector2 mapPos) {
		this.mapPos = mapPos;
	}

}
