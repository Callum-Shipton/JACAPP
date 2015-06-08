package Components.Graphical;

import Components.Message;
import Display.DPDTRenderer;
import Display.Renderer;
import Object.Entity;

public class AnimatedGraphics extends BaseGraphics {

	protected boolean animating;
	protected int animID = 0;
	protected int animTime = 6;
	
	@Override
	public void render(Entity e, Renderer r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Entity e) {
		
		if (isAnimating()) {
			animID++;
			if (animID >= image.getFWidth() * animTime)
				animID = 0;
		}
		
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
