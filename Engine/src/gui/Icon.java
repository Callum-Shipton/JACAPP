package gui;

import display.DPDTRenderer;
import display.Image;
import math.Vector2;

public class Icon extends GuiComponent {

	protected Vector2 size;
	protected Image i;
	private Vector2 currFrame;
	protected Vector2 maxFrame;
	protected boolean isAnimated;

	public Icon(float x, float y, Image i, boolean animating, float scale) {
		super(x, y);
		size = new Vector2((i.getWidth() / i.getFWidth()) * scale, (i.getHeight() / i.getFHeight()) * scale);
		setCurrFrame(new Vector2(0.0f, 0.0f));
		maxFrame = new Vector2(i.getFWidth(), i.getFHeight());
		this.i = i;
		this.isAnimated = animating;
	}

	public Vector2 getCurrFrame() {
		return this.currFrame;
	}

	public Image getI() {
		return this.i;
	}

	public Vector2 getSize() {
		return this.size;
	}

	@Override
	public void render(DPDTRenderer d) {
		d.draw(this.i, new Vector2(this.x, this.y), getSize(), 0.0f, getCurrFrame(), this.maxFrame);
	}

	public void setCurrFrame(Vector2 currFrame) {
		this.currFrame = currFrame;
	}

	public void setI(Image i) {
		this.i = i;
	}

	public void setMFrame(float w, float h) {
		this.maxFrame.set(w, h);
	}

	public void setSize(float w, float h) {
		this.size.set(w, h);
	}

	@Override
	public void update() {
		if (this.isAnimated) {
			if (this.currFrame.x() < this.maxFrame.x()) {
				this.currFrame.set(this.currFrame.x() + 1, this.currFrame.y());
			} else {
				this.currFrame.set(0.0f, this.currFrame.y());
			}
		}
	}
}
