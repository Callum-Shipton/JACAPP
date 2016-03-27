package gui;

import display.Art;
import display.DPDTRenderer;
import math.Vector2;

public class Counter2 {
	
	private int count;
	private float x;
	private float y;
	
	public Counter2(float x, float y, int count) {
		this.x = x;
		this.y = y;
		this.count = count;
	}

	public void update(int count){
		this.count = count;
	}
	
	public void render(DPDTRenderer r){
		
		Vector2 size = new Vector2(16,16);
		Vector2 maxTex = new Vector2(10,1);
		
		if(count < 10){
			Art.stat.draw(Art.numbers, new Vector2(x + 5, y), size, 0.0f, new Vector2(count,1), maxTex);
		} else {
			Art.stat.draw(Art.numbers, new Vector2(x + 5, y), size, 0.0f, new Vector2((int) Math.floor(count / 10),1), maxTex);
			Art.stat.draw(Art.numbers, new Vector2(x + 25, y), size, 0.0f, new Vector2(count % 10,1), maxTex);
		}
	}
}
