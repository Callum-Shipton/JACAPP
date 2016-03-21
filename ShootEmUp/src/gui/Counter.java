package gui;

import display.Art;
import display.DPDTRenderer;
import display.Image;
import math.Vector2;

public class Counter extends Icon{

	int count;
	float width;
	float x;
	float y;
	
	public Counter(float x, float y, float w, float h, Image i, boolean animating) {
		super(x, y, w, h, i, animating);
		width = w;
		this.x = x;
		this.y = y;
	}

	public void update(int count){
		super.update();
		this.count = count;
	}
	
	public void render(DPDTRenderer r){
		super.render(r);
		
		Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
		
		if(count < 10){
			Art.stat.draw(Art.numbers, new Vector2(x + width + 5, y), size, 0.0f, new Vector2(count,1), maxTex);
		} else {
			Art.stat.draw(Art.numbers, new Vector2(x + width + 5, y), size, 0.0f, new Vector2((int) Math.floor(count / 10),1), maxTex);
			Art.stat.draw(Art.numbers, new Vector2(x + width + 25, y), size, 0.0f, new Vector2(count % 10,1), maxTex);
		}
	}
}
