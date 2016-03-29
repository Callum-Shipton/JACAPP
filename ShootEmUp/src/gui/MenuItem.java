package gui;

import display.DPDTRenderer;

public abstract class MenuItem {
	
	float x;
	float y;
	
	public MenuItem(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void update();
	public abstract void render(DPDTRenderer d);
}
