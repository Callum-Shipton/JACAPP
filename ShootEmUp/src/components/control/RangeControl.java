package components.control;

import components.Message;
import components.graphical.AnimatedGraphics;
import components.movement.BaseMovement;
import display.Art;
import object.Entity;

public class RangeControl extends LineControl {

	
	private int range;
	
	public RangeControl(AnimatedGraphics AG, BaseMovement BM, int range) {
		super(AG, BM);
		this.range = (range*(Art.walls.getWidth() / Art.walls.getFWidth()))/BM.getSpeed();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(Entity e){
		super.update(e);
		range--;
		if(range <= 0){
			e.destroy();
			e.send(Message.ENTITY_DIED);
		}
	}

}
