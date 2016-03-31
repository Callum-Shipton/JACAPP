package gui;

import display.DPDTRenderer;

public abstract class GuiComponent {

	protected float x;
	protected float y;

	public GuiComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public abstract void update();

	public abstract void render(DPDTRenderer d);

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
}
