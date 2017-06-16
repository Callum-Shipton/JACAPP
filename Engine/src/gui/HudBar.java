package gui;

import display.DPDTRenderer;
import display.Image;
import math.Vector2;

public class HudBar extends Icon {

	private float value = 3;
	private float maxValue = 3;

	public HudBar(float x, float y, Image i, float scale) {
		super(x, y, i, false, scale);
	}

	public float getMaxValue() {
		return this.maxValue;
	}

	public float getValue() {
		return this.value;
	}

	@Override
	public void render(DPDTRenderer r) {
		for (int j = 0; j < this.maxValue; j++) {
			r.draw(this.i, new Vector2(this.x + (j * 10.0f), this.y), this.size, 0.0f,
					j >= this.value ? getCurrFrame() : new Vector2(0.0f, 1.0f), this.maxFrame);
		}
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
