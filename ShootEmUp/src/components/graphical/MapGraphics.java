package components.graphical;

import components.Message;
import display.Image;
import entity.Entity;
import math.Vector2;

public class MapGraphics extends BaseGraphics {

	private Vector2 mapPos;

	public MapGraphics(Image image, Vector2 mapPos, float x, float y) {
		this.image = image;
		this.width = image.getWidth() / image.getFWidth();
		this.height = image.getHeight() / image.getFHeight();
		setMapPos(mapPos);
		this.x = x;
		this.y = y;
	}

	public Vector2 getMapPos() {
		return this.mapPos;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Entity e) {
		// TODO Auto-generated method stub

	}

	public void setMapPos(Vector2 mapPos) {
		this.mapPos = mapPos;
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}

}
