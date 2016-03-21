package gui;

import math.Vector2;
import display.DPDTRenderer;
import display.Image;

public class HudBar extends Icon {
	
	private float value = 3;
	private float maxValue = 3;

	public HudBar(float x, float y, float w, float h, Image i) {
		super(x, y, w, h, i, false);
	}
	
	public void render(DPDTRenderer r){
		for(int j = 0; j < maxValue; j++){
			r.draw(i, new Vector2(pos.x()+(j*10.0f),pos.y()), size, 0.0f, j >= value ? getCurrFrame() : new Vector2(0.0f,1.0f), maxFrame);
		}
	}
	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

}
