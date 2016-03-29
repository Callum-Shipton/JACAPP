package gui;

import math.Vector2;
import display.DPDTRenderer;
import display.Image;

public class Icon extends MenuItem{
	
	protected Vector2 pos;
	protected Vector2 size;
	protected Image i;
	private Vector2 currFrame;
	protected Vector2 maxFrame;
	protected boolean isAnimated;

	public Icon(float x, float y, Image i, boolean animating, float scale){
		super(x, y);
		pos = new Vector2(x,y);
		size = new Vector2((i.getWidth()/i.getFWidth()) * scale,(i.getHeight()/i.getFHeight())*scale);
		setCurrFrame(new Vector2(0.0f, 0.0f));
		maxFrame = new Vector2(i.getFWidth(), i.getFHeight());
		this.i = i;
		isAnimated = animating;
	}
	
	public void render(DPDTRenderer d) {		
		d.draw(i, pos, getSize(), 0.0f, getCurrFrame(), maxFrame);
	}

	public void update(){
		if(isAnimated){
			if(currFrame.x() < maxFrame.x()){
				currFrame.set((currFrame.x() + 1), currFrame.y());
			} else {
				currFrame.set(0.0f, currFrame.y());
			}
		}
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

	public Vector2 getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(Vector2 currFrame) {
		this.currFrame = currFrame;
	}
	
	public void setI(Image i) {
		this.i = i;
	}

	public Image getI() {
		return i;
	}
}
