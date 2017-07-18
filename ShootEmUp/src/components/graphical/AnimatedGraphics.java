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

	public AnimatedGraphics(Image image, Renderer r, boolean animating, float scale) {
		this.image = image;
		this.width = (image.getWidth() / image.getFWidth()) * scale;
		this.height = (image.getHeight() / image.getFHeight()) * scale;
		this.r = r;
		this.animating = animating;
	}

	public AnimatedGraphics(Image image, Renderer r, boolean animating, float x, float y) {
		this.image = image;
		this.width = image.getWidth() / image.getFWidth();
		this.height = image.getHeight() / image.getFHeight();
		this.r = r;
		this.animating = animating;
		this.x = x;
		this.y = y;
	}

	public int getAnimID() {
		return this.animID;
	}

	public int getAnimTime() {
		return this.animTime;
	}

	public int getDirection() {
		return this.direction;
	}

	public boolean isAnimating() {
		return this.animating;
	}

	@Override
	public void render(Entity e) {
		((DPDTRenderer) this.r).draw(this.image, new Vector2f(getX(), getY()), new Vector2f(getWidth(), getHeight()),
				0.0f, new Vector2f(this.animID / this.animTime, getDirection()),
				new Vector2f(this.image.getFWidth(), this.image.getFHeight()));
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
			this.animID++;
			if (this.animID >= (this.image.getFWidth() * this.animTime)) {
				this.animID = 0;
			}
		}
	}
}
