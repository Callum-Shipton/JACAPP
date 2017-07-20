package components.graphical;

import org.joml.Vector2f;

import display.DPDTRenderer;
import display.Image;
import display.Renderer;
import entity.Entity;

public class AnimatedGraphics extends BaseGraphics {

	protected boolean animating;
	protected int animID = 0;
	protected int animTime = 6;
	protected int direction = 0;

	public AnimatedGraphics(Image image, Renderer r, boolean animating) {
		super(0,0,image,r);
		this.animating = animating;
	}

	public AnimatedGraphics(Image image, Renderer r, boolean animating, float x, float y) {
		this(image, r, animating);
		this.x = x;
		this.y = y;
	}

	public int getAnimID() {
		return animID;
	}

	public int getAnimTime() {
		return animTime;
	}

	public int getDirection() {
		return direction;
	}

	public boolean isAnimating() {
		return animating;
	}

	@Override
	public void render(Entity e) {
		((DPDTRenderer) r).draw(image, new Vector2f(getX(), getY()), new Vector2f(getWidth(), getHeight()), 0.0f,
				new Vector2f(animID / animTime, getDirection()), new Vector2f(image.getFWidth(), image.getFHeight()));
	}

	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	public void setAnimID(int animID) {
		this.animID = animID;
	}

	public void setAnimTime(int animTime) {
		this.animTime = animTime;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public void update(Entity e) {

		if (isAnimating()) {
			animID++;
			if (animID >= (image.getFWidth() * animTime)) {
				animID = 0;
			}
		}
	}
}
