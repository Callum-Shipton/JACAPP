package Display;

import Math.Vector2;

public class HudElement {
	
	private Vector2 pos;
	private Vector2 size;
	private Image i;
	private Vector2 currFrame;
	private Vector2 maxFrame;
	private float value = 0;
	
	public HudElement(float x, float y, float w, float h, Image i){
		pos = new Vector2(x,y);
		size = new Vector2(w,h);
		currFrame = new Vector2(0.0f, value);
		maxFrame = new Vector2(i.getFWidth(), i.getFHeight());
		this.i = i;
	}
	
	public void render(DPDTRenderer d) {		
		d.draw(i.getID(), pos, getSize(), 0.0f, currFrame, maxFrame);
	}

	public void update(float v){}
	
	public void setVal(float v){
		value = v;
		currFrame.set(0.0f, value);
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
