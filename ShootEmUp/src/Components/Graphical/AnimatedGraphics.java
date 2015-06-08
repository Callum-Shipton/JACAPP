package Components.Graphical;

import Components.Message;
import Display.DPDTRenderer;
import Display.Renderer;
import Math.Vector2;
import Object.Entity;

public class AnimatedGraphics extends BaseGraphics {

	protected boolean animating;
	protected int animID = 0;
	protected int animTime = 6;
	protected int direction;

	@Override
	public void update(Entity e) {
		
		if (isAnimating()) {
			animID++;
			if (animID >= image.getFWidth() * animTime)
				animID = 0;
		}
		
	}
	
	@Override
	public void render(Entity e, Renderer r) {
		((DPDTRenderer) r).draw(image, new Vector2(getX(), getY()), new Vector2(getWidth(), getHeight()),
				0.0f, new Vector2((float) Math.floor(animID / animTime),
						(float) direction), new Vector2(image.getFWidth(),
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

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

}
