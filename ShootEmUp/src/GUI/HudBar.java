package GUI;

import Display.DPDTRenderer;
import Display.Image;
import Math.Vector2;

public class HudBar extends HudElement {
	
	private float value = 3;
	private float maxValue = 3;

	public HudBar(float x, float y, float w, float h, Image i) {
		super(x, y, w, h, i);
	}
	
	public void render(DPDTRenderer r){
		for(int j = 0; j < maxValue; j++){
			r.draw(i, new Vector2(pos.x()+(j*10.0f),pos.y()), size, 0.0f, j > value ? currFrame : new Vector2(0.0f,1.0f), maxFrame);
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
