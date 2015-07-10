package components.graphical;

import math.Vector2;
import components.Message;
import display.DPDTRenderer;
import display.Image;
import display.Renderer;
import entities.Entity;

public class AnimatedGraphics extends BaseGraphics {

	protected boolean animating;
	protected int animID = 0;
	protected int animTime = 6;
	protected int direction = 0;

	public AnimatedGraphics(Image image, Renderer r, boolean animating, float x, float y){
		this.image = image;
		this.width = image.getWidth()/image.getFWidth();
		this.height = image.getHeight()/image.getFHeight();
		this.r = r;
		this.animating = animating;
		this.x = x;
		this.y = y;
	}
	
	public AnimatedGraphics(Image image, Renderer r, boolean animating){
		this.image = image;
		this.width = image.getWidth()/image.getFWidth();
		this.height = image.getHeight()/image.getFHeight();
		this.r = r;
		this.animating = animating;
	}
	
	@Override
	public void update(Entity e) {
		
		if (isAnimating()) {
			animID++;
			if (animID >= image.getFWidth() * animTime)
				animID = 0;
		}
	}
	
	@Override
	public void render(Entity e) {
		((DPDTRenderer) r).draw(image, new Vector2(getX(), getY()), new Vector2(getWidth(), getHeight()),
				0.0f, new Vector2((float) Math.floor(animID / animTime),
						(float) getDirection()), new Vector2(image.getFWidth(),
						image.getFHeight()));
	}
	
	public boolean isAnimating() {
		return animating;
	}

	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	public int getAnimID() {
		return animID;
	}

	public void setAnimID(int animID) {
		this.animID = animID;
	}

	public int getAnimTime() {
		return animTime;
	}

	public void setAnimTime(int animTime) {
		this.animTime = animTime;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}
