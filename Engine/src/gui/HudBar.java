package gui;

import org.joml.Vector2f;

import display.DPDTRenderer;
import display.Image;

public class HudBar extends Icon {

	private float value = 3;
	private float maxValue = 3;
	private float type;

	public HudBar(float x, float y, Image i, int type, float scale) {
		super(x, y, i, false, scale);
		this.type = type;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public float getValue() {
		return value;
	}

	@Override
	public void render(DPDTRenderer r) {
		for (int j = 0; j < maxValue; j++) {
			r.draw(i, new Vector2f(x + (j * 10.0f), y), size, 0.0f,
					j >= value ? getCurrFrame() : new Vector2f(type, 0.0f), maxFrame);
		}
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
