package gui;

import display.Art;
import display.DPDTRenderer;
import display.Image;
import math.Vector2;

public class Counter extends Icon{

	private int count;
	private float x;
	private float y;
	private Vector2 size;
	private int width;
	private final int FIRST_GAP = 5;
	private final int NUMBER_GAP = 25;
	private float scale;
	
	/*
	public Counter(float x, float y, float w, float h, Image i, boolean animating, int count) {
		super(x, y, w, h, i, animating);
		width = w;
		this.x = x;
		this.y = y;
		this.count = count;
	}
	*/
	
	public Counter(float x, float y, Image i, boolean animating, int count, float scale) {
		super(x, y, i, animating, scale);
		
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.count = count;
		width = (int) ((i.getWidth()/i.getFWidth()) * scale);
		size = new Vector2((i.getHeight()/i.getFHeight()) * scale,(i.getHeight()/i.getFHeight()) * scale);
	}

	public void update(int count){
		super.update();
		this.count = count;
	}
	
	public void render(DPDTRenderer r){
		super.render(r);
		
		Vector2 maxTex = new Vector2(10,1);
		
		if(count < 10){
			Art.stat.draw(Art.numbers, new Vector2(x + width + (FIRST_GAP * scale), y), size, 0.0f, new Vector2(count,1), maxTex);
		} else {
			Art.stat.draw(Art.numbers, new Vector2(x + width + (FIRST_GAP * scale), y), size, 0.0f, new Vector2((int) Math.floor(count / 10),1), maxTex);
			Art.stat.draw(Art.numbers, new Vector2(x + width + (NUMBER_GAP * scale), y), size, 0.0f, new Vector2(count % 10,1), maxTex);
		}
	}
}
