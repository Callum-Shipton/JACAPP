package gui;

import display.DPDTRenderer;

public abstract class GuiComponent {

	protected float x = 0;
	protected float y = 0;
	protected float height = 0;
	protected float width = 0;

	public GuiComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public GuiComponent(float x, float y, float height, float width) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	public GuiComponent() {
	}

	public abstract void render(DPDTRenderer d);

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public abstract void update();

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
