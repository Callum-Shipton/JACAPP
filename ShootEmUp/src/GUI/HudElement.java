package GUI;

import Display.DPDTRenderer;
import Display.Image;
import Math.Vector2;

public class HudElement extends GuiComponent {
	
	protected Vector2 pos;
	protected Vector2 size;
	protected Image i;
	protected Vector2 currFrame;
	protected Vector2 maxFrame;

	
	
	public HudElement(float x, float y, float w, float h, Image i){
		pos = new Vector2(x,y);
		size = new Vector2(w,h);
		currFrame = new Vector2(0.0f, 0.0f);
		maxFrame = new Vector2(i.getFWidth(), i.getFHeight());
		this.i = i;
	}
	
	public void render(DPDTRenderer d) {		
		d.draw(i, pos, getSize(), 0.0f, currFrame, maxFrame);
	}

	public void update(float v){
		
	}
	
	
	public void setMFrame(float w, float h){
		maxFrame.set(w, h);
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(float w, float h) {
		size.set(w, h);
	}
}
