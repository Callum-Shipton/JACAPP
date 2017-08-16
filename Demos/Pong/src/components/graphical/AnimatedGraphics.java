package components.graphical;

import org.joml.Vector2f;

import display.DPDTRenderer;
import display.BaseRenderSystem;
import entity.Entity;

public class AnimatedGraphics extends BaseGraphics {

	protected transient boolean animating;
	protected transient int animID = 0;
	protected transient int animTime = 6;
	protected transient int direction = 0;

	public AnimatedGraphics(String imageId, boolean animating) {
		super(imageId, BaseRenderSystem.stat);
		this.animating = animating;
	}

	public AnimatedGraphics(String imageId, boolean animating, float x, float y) {
		this(imageId, animating);
		this.x = x;
		this.y = y;
	}

	public AnimatedGraphics(AnimatedGraphics animatedGraphics) {
		this(animatedGraphics.imageId, true);
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
	public void update() {

		if (isAnimating()) {
			animID++;
			if (animID >= (image.getFWidth() * animTime)) {
				animID = 0;
			}
		}
	}
}
