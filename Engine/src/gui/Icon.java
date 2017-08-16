package gui;

import org.joml.Vector2f;

import display.DPDTRenderer;
import display.Image;

public class Icon extends GuiComponent {

	protected Vector2f size;
	protected Image i;
	protected Vector2f currFrame;
	protected Vector2f maxFrame;
	protected boolean isAnimated;

	public Icon(float x, float y, Image i, boolean animating, float scale) {
		super(x, y);
		size = new Vector2f((i.getWidth() / i.getFWidth()) * scale, (i.getHeight() / i.getFHeight()) * scale);
		setCurrFrame(new Vector2f(0.0f, 0.0f));
		maxFrame = new Vector2f(i.getFWidth(), i.getFHeight());
		this.i = i;
		isAnimated = animating;
	}

	public Vector2f getCurrFrame() {
		return currFrame;
	}

	public Image getI() {
		return i;
	}

	public Vector2f getSize() {
		return size;
	}

	@Override
	public void render(DPDTRenderer d) {
		d.draw(i, new Vector2f(x, y), getSize(), 0.0f, getCurrFrame(), maxFrame);
	}

	public void setCurrFrame(Vector2f currFrame) {
		this.currFrame = currFrame;
	}

	public void setI(Image i) {
		this.i = i;
	}

	public void setMFrame(float w, float h) {
		maxFrame.set(w, h);
	}

	public void setSize(float w, float h) {
		size.set(w, h);
	}

	@Override
	public void update() {
		if (isAnimated) {
			if (currFrame.x() < maxFrame.x()) {
				currFrame.set(currFrame.x() + 1, currFrame.y());
			} else {
				currFrame.set(0.0f, currFrame.y());
			}
		}
	}
}
