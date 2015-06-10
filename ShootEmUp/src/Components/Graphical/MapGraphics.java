package Components.Graphical;

import Components.Message;
import Display.Image;
import Display.Renderer;
import Math.Vector2;
import Object.Entity;

public class MapGraphics extends BaseGraphics{
	
	private Vector2 mapPos;
	
	public MapGraphics(Image image, Vector2 mapPos, float x, float y){
		this.image = image;
		this.width = image.getWidth()/image.getFWidth();
		this.height = image.getHeight()/image.getFHeight();
		this.setMapPos(mapPos);
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
